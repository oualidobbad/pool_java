package com.transactions.controller;

import com.transactions.core.*;
import com.transactions.ui.components.*;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.UUID;

/**
 * Main controller – builds every view, wires user interactions to core logic.
 */
public class MainController {

	/* ---- Core service (original business logic) ---- */
	private final TransactionsService service = new TransactionsService();

	/* ---- Layout shells ---- */
	private final BorderPane root = new BorderPane();
	private final StackPane contentStack = new StackPane(); // holds current panel + toasts
	private final VBox navBox = new VBox();
	private ToggleGroup navGroup;

	/* ---- Panels ---- */
	private Node addUserPanel;
	private Node viewBalancePanel;
	private Node transferPanel;
	private Node viewTransactionsPanel;
	private Node removeTransferPanel;
	private Node checkValidityPanel;

	/* ---- Observable user table data (for the "View Balance" panel) ---- */
	private final ObservableList<UserRow> userRows = FXCollections.observableArrayList();

	// ==================================================================
	// Public entry point
	// ==================================================================

	public BorderPane buildUI() {
		buildSidebar();
		buildPanels();

		/* Content wrapper with padding */
		StackPane contentWrapper = new StackPane(contentStack);
		contentWrapper.setPadding(new Insets(28, 32, 28, 32));
		contentWrapper.getStyleClass().add("content-root");
		VBox.setVgrow(contentWrapper, Priority.ALWAYS);
		HBox.setHgrow(contentWrapper, Priority.ALWAYS);

		root.setLeft(navBox);
		root.setCenter(contentWrapper);

		showPanel(addUserPanel);
		return root;
	}

	// ==================================================================
	// Sidebar
	// ==================================================================

	private void buildSidebar() {
		navBox.getStyleClass().add("sidebar");
		navBox.setPadding(new Insets(24, 16, 24, 16));
		navBox.setSpacing(4);

		/* Logo */
		Label logo = new Label("💰  TransactPro");
		logo.getStyleClass().add("logo-label");
		Label sub = new Label("Transaction Management System");
		sub.getStyleClass().add("logo-sub");
		VBox logoBox = new VBox(2, logo, sub);
		logoBox.setPadding(new Insets(0, 0, 20, 4));

		/* Section labels */
		Label mainSection = sectionLabel("MAIN");
		Label devSection = sectionLabel("DEVELOPER");

		/* Nav buttons */
		navGroup = new ToggleGroup();

		ToggleButton btnAdd = navButton("➕  Add User", navGroup);
		ToggleButton btnBalance = navButton("👤  View Balances", navGroup);
		ToggleButton btnTransfer = navButton("💸  Perform Transfer", navGroup);
		ToggleButton btnTxns = navButton("📋  View Transactions", navGroup);
		ToggleButton btnRemove = navButton("🗑  Remove Transfer", navGroup);
		ToggleButton btnCheck = navButton("🔍  Check Validity", navGroup);

		Separator sep = new Separator();
		sep.getStyleClass().add("nav-separator");
		sep.setPadding(new Insets(8, 0, 4, 0));

		/* Wire nav clicks */
		btnAdd.setOnAction(e -> showPanel(addUserPanel));
		btnBalance.setOnAction(e -> {
			refreshUserRows();
			showPanel(viewBalancePanel);
		});
		btnTransfer.setOnAction(e -> showPanel(transferPanel));
		btnTxns.setOnAction(e -> showPanel(viewTransactionsPanel));
		btnRemove.setOnAction(e -> showPanel(removeTransferPanel));
		btnCheck.setOnAction(e -> showPanel(checkValidityPanel));

		Region spacer = new Region();
		VBox.setVgrow(spacer, Priority.ALWAYS);

		Label version = new Label("v1.0.0");
		version.getStyleClass().add("logo-sub");
		version.setPadding(new Insets(0, 0, 0, 4));

		navBox.getChildren().addAll(
				logoBox,
				mainSection,
				btnAdd, btnBalance, btnTransfer, btnTxns,
				sep,
				devSection,
				btnRemove, btnCheck,
				spacer,
				version);

		btnAdd.setSelected(true);
	}

	private ToggleButton navButton(String text, ToggleGroup group) {
		ToggleButton btn = new ToggleButton(text);
		btn.getStyleClass().add("nav-btn");
		btn.setToggleGroup(group);
		btn.setMaxWidth(Double.MAX_VALUE);

		/* Keep one always selected */
		btn.selectedProperty().addListener((obs, wasSelected, isNow) -> {
			if (isNow)
				btn.getStyleClass().add("active");
			else
				btn.getStyleClass().remove("active");
		});
		return btn;
	}

	private Label sectionLabel(String txt) {
		Label l = new Label(txt);
		l.getStyleClass().add("section-label");
		return l;
	}

	// ==================================================================
	// Panel switching
	// ==================================================================

	private void showPanel(Node panel) {
		if (!contentStack.getChildren().isEmpty()) {
			Node old = contentStack.getChildren().get(0);
			Animations.crossFade(old, panel, contentStack);
		} else {
			contentStack.getChildren().add(panel);
			StackPane.setAlignment(panel, Pos.TOP_LEFT);
			Animations.fadeSlideIn(panel);
		}
	}

	private void toast(String msg, Toast.Type type) {
		Toast.show(contentStack, msg, type);
	}

	// ==================================================================
	// Build all panels
	// ==================================================================

	private void buildPanels() {
		addUserPanel = buildAddUserPanel();
		viewBalancePanel = buildViewBalancePanel();
		transferPanel = buildTransferPanel();
		viewTransactionsPanel = buildViewTransactionsPanel();
		removeTransferPanel = buildRemoveTransferPanel();
		checkValidityPanel = buildCheckValidityPanel();
	}

	/*
	 * ----------------------------------------------------------
	 * 1) ADD USER
	 * ----------------------------------------------------------
	 */
	private Node buildAddUserPanel() {
		Label title = panelTitle("Add a New User");
		Label subtitle = panelSubtitle("Create a user account with an initial balance");

		TextField nameField = styledField("Full name");
		TextField balanceField = styledField("Initial balance");

		Button submit = new Button("Create User");
		submit.getStyleClass().add("btn-primary");
		submit.setMaxWidth(Double.MAX_VALUE);

		Label resultLabel = new Label();
		resultLabel.setWrapText(true);

		submit.setOnAction(e -> {
			String name = nameField.getText().trim();
			String balText = balanceField.getText().trim();
			nameField.getStyleClass().remove("error");
			balanceField.getStyleClass().remove("error");

			if (name.isEmpty()) {
				nameField.getStyleClass().add("error");
				toast("Name is required", Toast.Type.ERROR);
				return;
			}
			int balance;
			try {
				balance = Integer.parseInt(balText);
			} catch (Exception ex) {
				balanceField.getStyleClass().add("error");
				toast("Balance must be a number", Toast.Type.ERROR);
				return;
			}

			try {
				User user = new User(name, balance);
				service.addUser(user);
				toast("User \"" + name + "\" created  (ID: " + user.getIdentifier() + ")", Toast.Type.SUCCESS);
				nameField.clear();
				balanceField.clear();
			} catch (Exception ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			}
		});

		VBox form = new VBox(14,
				fieldGroup("User Name", nameField),
				fieldGroup("Initial Balance ($)", balanceField),
				submit);
		form.setMaxWidth(420);

		VBox card = card(title, subtitle, form);
		return card;
	}

	/*
	 * ----------------------------------------------------------
	 * 2) VIEW BALANCES (as table)
	 * ----------------------------------------------------------
	 */
	@SuppressWarnings("unchecked")
	private Node buildViewBalancePanel() {
		Label title = panelTitle("User Balances");
		Label subtitle = panelSubtitle("Click Refresh to load the current user list");

		TableView<UserRow> table = new TableView<>(userRows);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label("No users yet — add one first."));

		TableColumn<UserRow, Number> colId = new TableColumn<>("ID");
		colId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getId()));
		colId.setPrefWidth(60);
		colId.setCellFactory(col -> labelCell(item -> String.valueOf(item.intValue())));

		TableColumn<UserRow, String> colName = new TableColumn<>("Name");
		colName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));
		colName.setCellFactory(col -> labelCell(item -> item));

		TableColumn<UserRow, String> colBalance = new TableColumn<>("Balance");
		colBalance.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getBalanceDisplay()));
		colBalance.setPrefWidth(140);
		colBalance.setCellFactory(col -> labelCell(item -> item));

		table.getColumns().addAll(colId, colName, colBalance);
		VBox.setVgrow(table, Priority.ALWAYS);

		/* Individual lookup */
		TextField lookupField = styledField("User ID");
		lookupField.setPrefWidth(160);
		Button lookupBtn = new Button("Lookup");
		lookupBtn.getStyleClass().add("btn-secondary");
		Label lookupResult = new Label();
		lookupResult.getStyleClass().add("field-label");

		lookupBtn.setOnAction(e -> {
			try {
				int id = Integer.parseInt(lookupField.getText().trim());
				int bal = service.getBalanceUser(id);
				User u = service.getUserById(id);
				lookupResult.setText(u.getName() + "  —  $" + bal);
				Animations.fadeIn(lookupResult);
			} catch (NumberFormatException ex) {
				toast("Enter a valid numeric ID", Toast.Type.ERROR);
			} catch (UserNotFoundException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
				lookupResult.setText("");
			}
		});

		HBox lookupRow = new HBox(10, lookupField, lookupBtn, lookupResult);
		lookupRow.setAlignment(Pos.CENTER_LEFT);

		Button refreshBtn = new Button("Refresh List");
		refreshBtn.getStyleClass().add("btn-primary");
		refreshBtn.setOnAction(e -> {
			refreshUserRows();
			toast("User list refreshed", Toast.Type.INFO);
		});

		HBox topActions = new HBox(12, refreshBtn);
		topActions.setAlignment(Pos.CENTER_LEFT);

		VBox card = card(title, subtitle, lookupRow, new Separator(), topActions, table);
		VBox.setVgrow(card, Priority.ALWAYS);
		return card;
	}

	/*
	 * ----------------------------------------------------------
	 * 3) PERFORM TRANSFER
	 * ----------------------------------------------------------
	 */
	private Node buildTransferPanel() {
		Label title = panelTitle("Perform a Transfer");
		Label subtitle = panelSubtitle("Move funds between two user accounts");

		TextField senderField = styledField("Sender ID");
		TextField recipientField = styledField("Recipient ID");
		TextField amountField = styledField("Amount ($)");

		Button submit = new Button("Execute Transfer");
		submit.getStyleClass().add("btn-primary");
		submit.setMaxWidth(Double.MAX_VALUE);

		submit.setOnAction(e -> {
			senderField.getStyleClass().remove("error");
			recipientField.getStyleClass().remove("error");
			amountField.getStyleClass().remove("error");
			try {
				int sid = parseIntField(senderField, "Sender ID");
				int rid = parseIntField(recipientField, "Recipient ID");
				int amt = parseIntField(amountField, "Amount");

				service.transferTransaction(sid, rid, amt);
				toast("Transfer of $" + amt + " completed successfully", Toast.Type.SUCCESS);
				senderField.clear();
				recipientField.clear();
				amountField.clear();
			} catch (IllegalTransactionException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			} catch (UserNotFoundException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			} catch (ValidationException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			}
		});

		VBox form = new VBox(14,
				fieldGroup("Sender ID", senderField),
				fieldGroup("Recipient ID", recipientField),
				fieldGroup("Transfer Amount ($)", amountField),
				submit);
		form.setMaxWidth(420);

		return card(title, subtitle, form);
	}

	/*
	 * ----------------------------------------------------------
	 * 4) VIEW TRANSACTIONS
	 * ----------------------------------------------------------
	 */
	@SuppressWarnings("unchecked")
	private Node buildViewTransactionsPanel() {
		Label title = panelTitle("User Transactions");
		Label subtitle = panelSubtitle("View all transactions for a specific user");

		TextField idField = styledField("User ID");
		idField.setPrefWidth(160);
		Button fetchBtn = new Button("Fetch");
		fetchBtn.getStyleClass().add("btn-primary");

		HBox topRow = new HBox(10, fieldGroup("User ID", idField), fetchBtn);
		topRow.setAlignment(Pos.BOTTOM_LEFT);

		ObservableList<TxnRow> txnRows = FXCollections.observableArrayList();
		TableView<TxnRow> table = new TableView<>(txnRows);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label("Enter a User ID and click Fetch."));

		TableColumn<TxnRow, String> colDir = new TableColumn<>("Type");
		colDir.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getDirection()));
		colDir.setPrefWidth(80);
		colDir.setCellFactory(col -> new TableCell<>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setGraphic(null);
					return;
				}
				Label badge = new Label(item);
				badge.getStyleClass().addAll("badge", item.equals("CREDIT") ? "badge-credit" : "badge-debit");
				setGraphic(badge);
			}
		});

		TableColumn<TxnRow, String> colPeer = new TableColumn<>("Peer");
		colPeer.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getPeer()));
		colPeer.setCellFactory(col -> labelCell(item -> item));

		TableColumn<TxnRow, String> colAmt = new TableColumn<>("Amount");
		colAmt.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getAmountDisplay()));
		colAmt.setPrefWidth(120);
		colAmt.setCellFactory(col -> labelCell(item -> item));

		TableColumn<TxnRow, String> colUuid = new TableColumn<>("Transaction ID");
		colUuid.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getUuid()));
		colUuid.setPrefWidth(270);
		colUuid.setCellFactory(col -> labelCell(item -> item));

		table.getColumns().addAll(colDir, colPeer, colAmt, colUuid);
		VBox.setVgrow(table, Priority.ALWAYS);

		fetchBtn.setOnAction(e -> {
			txnRows.clear();
			try {
				int uid = Integer.parseInt(idField.getText().trim());
				Transaction[] txns = service.getTransactions(uid);
				if (txns.length == 0) {
					toast("No transactions for this user", Toast.Type.INFO);
					return;
				}
				for (Transaction t : txns) {
					boolean isCredit = t.getCategory() == TransferCategory.CREDIT;
					User peer = isCredit ? t.getSender() : t.getRecipient();
					String peerStr = (isCredit ? "From " : "To ") + peer.getName() + " (ID: " + peer.getIdentifier()
							+ ")";
					txnRows.add(new TxnRow(
							isCredit ? "CREDIT" : "DEBIT",
							peerStr,
							"$" + t.getTransferAmount(),
							t.getIdentifier().toString()));
				}
				toast(txns.length + " transaction(s) loaded", Toast.Type.SUCCESS);
			} catch (NumberFormatException ex) {
				toast("Enter a valid numeric ID", Toast.Type.ERROR);
			} catch (UserNotFoundException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			}
		});

		VBox card = card(title, subtitle, topRow, table);
		VBox.setVgrow(card, Priority.ALWAYS);
		return card;
	}

	/*
	 * ----------------------------------------------------------
	 * 5) REMOVE TRANSFER (DEV)
	 * ----------------------------------------------------------
	 */
	private Node buildRemoveTransferPanel() {
		Label title = panelTitle("Remove a Transfer");
		Label subtitle = panelSubtitle("DEV — Delete a specific transaction by UUID");

		Label devBadge = new Label("⚠  DEVELOPER TOOL");
		devBadge.setStyle(
				"-fx-background-color: #fef3c7; -fx-text-fill: #92400e; -fx-padding: 6 14; -fx-background-radius: 6; -fx-font-weight: bold; -fx-font-size: 11px;");

		TextField userIdField = styledField("User ID");
		TextField txnIdField = styledField("Transaction UUID");

		Button submit = new Button("Remove Transfer");
		submit.getStyleClass().add("btn-danger");
		submit.setMaxWidth(Double.MAX_VALUE);

		submit.setOnAction(e -> {
			userIdField.getStyleClass().remove("error");
			txnIdField.getStyleClass().remove("error");
			try {
				int uid = parseIntField(userIdField, "User ID");
				UUID txnId;
				try {
					txnId = UUID.fromString(txnIdField.getText().trim());
				} catch (Exception ex) {
					txnIdField.getStyleClass().add("error");
					toast("Invalid UUID format", Toast.Type.ERROR);
					return;
				}
				service.removeTransaction(uid, txnId);
				toast("Transfer removed successfully", Toast.Type.SUCCESS);
				userIdField.clear();
				txnIdField.clear();
			} catch (UserNotFoundException | TransactionNotFoundException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			} catch (ValidationException ex) {
				toast(ex.getMessage(), Toast.Type.ERROR);
			}
		});

		VBox form = new VBox(14,
				fieldGroup("User ID", userIdField),
				fieldGroup("Transaction UUID", txnIdField),
				submit);
		form.setMaxWidth(520);

		return card(title, subtitle, devBadge, form);
	}

	/*
	 * ----------------------------------------------------------
	 * 6) CHECK VALIDITY (DEV)
	 * ----------------------------------------------------------
	 */
	@SuppressWarnings("unchecked")
	private Node buildCheckValidityPanel() {
		Label title = panelTitle("Check Transfer Validity");
		Label subtitle = panelSubtitle("DEV — Scan for unpaired / orphaned transactions");

		Label devBadge = new Label("⚠  DEVELOPER TOOL");
		devBadge.setStyle(
				"-fx-background-color: #fef3c7; -fx-text-fill: #92400e; -fx-padding: 6 14; -fx-background-radius: 6; -fx-font-weight: bold; -fx-font-size: 11px;");

		ObservableList<UnpairedRow> rows = FXCollections.observableArrayList();
		TableView<UnpairedRow> table = new TableView<>(rows);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPlaceholder(new Label("Click \"Run Check\" to scan."));

		TableColumn<UnpairedRow, String> colRecip = new TableColumn<>("Recipient");
		colRecip.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getRecipient()));
		colRecip.setCellFactory(col -> labelCell(item -> item));

		TableColumn<UnpairedRow, String> colSender = new TableColumn<>("Sender");
		colSender.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getSender()));
		colSender.setCellFactory(col -> labelCell(item -> item));

		TableColumn<UnpairedRow, String> colAmt = new TableColumn<>("Amount");
		colAmt.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getAmount()));
		colAmt.setPrefWidth(100);
		colAmt.setCellFactory(col -> labelCell(item -> item));

		TableColumn<UnpairedRow, String> colUuid = new TableColumn<>("Transaction ID");
		colUuid.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getUuid()));
		colUuid.setPrefWidth(270);
		colUuid.setCellFactory(col -> labelCell(item -> item));

		table.getColumns().addAll(colRecip, colSender, colAmt, colUuid);
		VBox.setVgrow(table, Priority.ALWAYS);

		Button runBtn = new Button("Run Validity Check");
		runBtn.getStyleClass().add("btn-primary");

		Label resultSummary = new Label();
		resultSummary.getStyleClass().add("field-label");

		runBtn.setOnAction(e -> {
			rows.clear();
			Transaction[] unpaired = service.checkValidityOfTransactions();
			if (unpaired.length == 0) {
				toast("All transactions are properly paired", Toast.Type.SUCCESS);
				resultSummary.setText("✓  All transfers valid");
				resultSummary.setStyle("-fx-text-fill: #16a34a;");
			} else {
				for (Transaction t : unpaired) {
					User r = t.getRecipient();
					User s = t.getSender();
					rows.add(new UnpairedRow(
							r.getName() + " (ID: " + r.getIdentifier() + ")",
							s.getName() + " (ID: " + s.getIdentifier() + ")",
							"$" + t.getTransferAmount(),
							t.getIdentifier().toString()));
				}
				toast(unpaired.length + " unpaired transaction(s) found", Toast.Type.ERROR);
				resultSummary.setText("⚠  " + unpaired.length + " unpaired transaction(s)");
				resultSummary.setStyle("-fx-text-fill: #dc2626;");
			}
			Animations.fadeIn(resultSummary);
		});

		HBox actions = new HBox(14, runBtn, resultSummary);
		actions.setAlignment(Pos.CENTER_LEFT);

		VBox card = card(title, subtitle, devBadge, actions, table);
		VBox.setVgrow(card, Priority.ALWAYS);
		return card;
	}

	// ==================================================================
	// Helpers
	// ==================================================================

	private Label panelTitle(String text) {
		Label l = new Label(text);
		l.getStyleClass().add("panel-title");
		return l;
	}

	private Label panelSubtitle(String text) {
		Label l = new Label(text);
		l.getStyleClass().add("panel-subtitle");
		return l;
	}

	private TextField styledField(String prompt) {
		TextField f = new TextField();
		f.setPromptText(prompt);
		f.setMaxWidth(Double.MAX_VALUE);
		return f;
	}

	private VBox fieldGroup(String labelText, Node field) {
		Label lbl = new Label(labelText);
		lbl.getStyleClass().add("field-label");
		VBox box = new VBox(4, lbl, field);
		return box;
	}

	private VBox card(Node... children) {
		VBox box = new VBox(16, children);
		box.getStyleClass().add("card");
		return box;
	}

	/**
	 * Renders table cells using Label graphics — works around garbled Text
	 * rendering on Linux.
	 */
	private <S, T> TableCell<S, T> labelCell(java.util.function.Function<T, String> formatter) {
		return new TableCell<>() {
			private final Label lbl = new Label();
			{
				lbl.setStyle("-fx-text-fill: #1e293b; -fx-font-size: 13px;");
			}

			@Override
			protected void updateItem(T item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setGraphic(null);
					setText(null);
				} else {
					setText(null);
					lbl.setText(formatter.apply(item));
					setGraphic(lbl);
				}
			}
		};
	}

	private int parseIntField(TextField field, String name) {
		try {
			return Integer.parseInt(field.getText().trim());
		} catch (NumberFormatException e) {
			field.getStyleClass().add("error");
			throw new ValidationException(name + " must be a valid number");
		}
	}

	private void refreshUserRows() {
		userRows.clear();
		int count = service.getUserCount();
		for (int i = 0; i < count; i++) {
			User u = service.getUserByIndex(i);
			userRows.add(new UserRow(u.getIdentifier(), u.getName(), "$" + u.getBalance()));
		}
	}

	// ==================================================================
	// Simple row model classes for TableView
	// ==================================================================

	/** Internal validation exception. */
	private static class ValidationException extends RuntimeException {
		ValidationException(String msg) {
			super(msg);
		}
	}

	public static class UserRow {
		private final int id;
		private final String name;
		private final String balanceDisplay;

		public UserRow(int id, String name, String balanceDisplay) {
			this.id = id;
			this.name = name;
			this.balanceDisplay = balanceDisplay;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getBalanceDisplay() {
			return balanceDisplay;
		}
	}

	public static class TxnRow {
		private final String direction;
		private final String peer;
		private final String amountDisplay;
		private final String uuid;

		public TxnRow(String direction, String peer, String amountDisplay, String uuid) {
			this.direction = direction;
			this.peer = peer;
			this.amountDisplay = amountDisplay;
			this.uuid = uuid;
		}

		public String getDirection() {
			return direction;
		}

		public String getPeer() {
			return peer;
		}

		public String getAmountDisplay() {
			return amountDisplay;
		}

		public String getUuid() {
			return uuid;
		}
	}

	public static class UnpairedRow {
		private final String recipient;
		private final String sender;
		private final String amount;
		private final String uuid;

		public UnpairedRow(String recipient, String sender, String amount, String uuid) {
			this.recipient = recipient;
			this.sender = sender;
			this.amount = amount;
			this.uuid = uuid;
		}

		public String getRecipient() {
			return recipient;
		}

		public String getSender() {
			return sender;
		}

		public String getAmount() {
			return amount;
		}

		public String getUuid() {
			return uuid;
		}
	}
}
