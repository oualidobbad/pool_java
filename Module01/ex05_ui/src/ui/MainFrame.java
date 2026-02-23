package ui;

import logic.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.UUID;

/**
 * Main application frame — houses sidebar navigation and content panels.
 */
public class MainFrame extends JFrame {

    private final TransactionsService service = new TransactionsService();
    private final boolean devMode;

    // Sidebar buttons
    private StyledButton btnAddUser, btnBalance, btnTransfer, btnViewTx, btnRemoveTx, btnCheckValidity, btnExit;
    private StyledButton activeButton = null;

    // Content area
    private JPanel contentArea;
    private CardLayout cardLayout;

    // Log area
    private JTextPane logPane;
    private StyledDocument logDoc;

    // Panels
    private static final String CARD_WELCOME   = "welcome";
    private static final String CARD_ADD_USER  = "addUser";
    private static final String CARD_BALANCE   = "balance";
    private static final String CARD_TRANSFER  = "transfer";
    private static final String CARD_VIEW_TX   = "viewTx";
    private static final String CARD_REMOVE_TX = "removeTx";
    private static final String CARD_CHECK_VAL = "checkVal";

    public MainFrame(boolean devMode) {
        this.devMode = devMode;

        setTitle("Transactions Manager" + (devMode ? " [DEV]" : ""));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainContent(), BorderLayout.CENTER);

        // Start with welcome
        cardLayout.show(contentArea, CARD_WELCOME);
    }

    // ═══════════════════════════ SIDEBAR ═══════════════════════════

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.BG_SURFACE);
        sidebar.setPreferredSize(new Dimension(Theme.SIDEBAR_WIDTH, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("  \uD83D\uDCB3 TxManager");
        title.setFont(Theme.FONT_TITLE);
        title.setForeground(Theme.ACCENT_BLUE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(title);
        sidebar.add(Box.createVerticalStrut(8));

        JLabel subtitle = new JLabel("  Transactions Service");
        subtitle.setFont(Theme.FONT_SMALL);
        subtitle.setForeground(Theme.TEXT_DIM);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(subtitle);
        sidebar.add(Box.createVerticalStrut(25));

        // Section label
        sidebar.add(sectionLabel("OPERATIONS"));
        sidebar.add(Box.createVerticalStrut(6));

        btnAddUser  = addNavButton(sidebar, "\u2795  Add User",          CARD_ADD_USER);
        btnBalance  = addNavButton(sidebar, "\uD83D\uDCCA  View Balance",        CARD_BALANCE);
        btnTransfer = addNavButton(sidebar, "\u21C4  Transfer",          CARD_TRANSFER);
        btnViewTx   = addNavButton(sidebar, "\uD83D\uDCCB  Transactions",        CARD_VIEW_TX);

        if (devMode) {
            sidebar.add(Box.createVerticalStrut(15));
            sidebar.add(sectionLabel("DEV TOOLS"));
            sidebar.add(Box.createVerticalStrut(6));
            btnRemoveTx     = addNavButton(sidebar, "\uD83D\uDDD1  Remove Transfer", CARD_REMOVE_TX);
            btnCheckValidity = addNavButton(sidebar, "\u2714  Check Validity", CARD_CHECK_VAL);
        }

        sidebar.add(Box.createVerticalGlue());

        // Dev mode badge
        if (devMode) {
            JLabel devLabel = new JLabel("  DEV MODE");
            devLabel.setFont(Theme.FONT_SMALL);
            devLabel.setForeground(Theme.ACCENT_YELLOW);
            sidebar.add(devLabel);
            sidebar.add(Box.createVerticalStrut(8));
        }

        btnExit = new StyledButton("\u23FB  Exit", Theme.ACCENT_RED, new Color(200, 100, 120), Theme.BG_DARK);
        btnExit.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnExit.addActionListener(e -> {
            int res = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) System.exit(0);
        });
        sidebar.add(btnExit);

        return sidebar;
    }

    private JLabel sectionLabel(String text) {
        JLabel label = new JLabel("  " + text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(Theme.TEXT_DIM);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private StyledButton addNavButton(JPanel parent, String text, String card) {
        StyledButton btn = new StyledButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> switchToCard(card, btn));
        parent.add(btn);
        parent.add(Box.createVerticalStrut(5));
        return btn;
    }

    private void switchToCard(String card, StyledButton btn) {
        if (activeButton != null) activeButton.setSelected(false);
        btn.setSelected(true);
        activeButton = btn;

        // Fade transition
        contentArea.setVisible(false);
        cardLayout.show(contentArea, card);
        contentArea.setVisible(true);

        // Fade-in the content
        Timer fadeTimer = new Timer(12, null);
        final float[] alpha = {0f};
        fadeTimer.addActionListener(e -> {
            alpha[0] += 0.1f;
            if (alpha[0] >= 1f) {
                alpha[0] = 1f;
                fadeTimer.stop();
            }
            // Repaint with slight delay effect
            contentArea.repaint();
        });
        fadeTimer.start();
    }

    // ═══════════════════════════ MAIN CONTENT ═══════════════════════════

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(0, 10));
        main.setBackground(Theme.BG_DARK);
        main.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Content cards
        cardLayout = new CardLayout();
        contentArea = new JPanel(cardLayout);
        contentArea.setOpaque(false);

        contentArea.add(createWelcomePanel(),    CARD_WELCOME);
        contentArea.add(createAddUserPanel(),    CARD_ADD_USER);
        contentArea.add(createBalancePanel(),    CARD_BALANCE);
        contentArea.add(createTransferPanel(),   CARD_TRANSFER);
        contentArea.add(createViewTxPanel(),     CARD_VIEW_TX);
        if (devMode) {
            contentArea.add(createRemoveTxPanel(),   CARD_REMOVE_TX);
            contentArea.add(createCheckValPanel(),   CARD_CHECK_VAL);
        }

        main.add(contentArea, BorderLayout.CENTER);

        // Log panel at bottom
        main.add(createLogPanel(), BorderLayout.SOUTH);

        return main;
    }

    // ── Welcome Panel ──

    private JPanel createWelcomePanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new GridBagLayout());

        JPanel center = new JPanel();
        center.setOpaque(false);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("\uD83D\uDCB3");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcome = new JLabel("Welcome to Transactions Manager");
        welcome.setFont(Theme.FONT_TITLE);
        welcome.setForeground(Theme.TEXT_PRIMARY);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel desc = new JLabel("Select an operation from the sidebar to get started");
        desc.setFont(Theme.FONT_BODY);
        desc.setForeground(Theme.TEXT_SECONDARY);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(icon);
        center.add(Box.createVerticalStrut(15));
        center.add(welcome);
        center.add(Box.createVerticalStrut(8));
        center.add(desc);

        panel.add(center);
        return panel;
    }

    // ── Add User Panel ──

    private JPanel createAddUserPanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel heading = styledHeading("\u2795  Add New User");
        panel.add(heading, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(styledLabel("Username:"), gbc);
        JTextField nameField = styledTextField(20);
        gbc.gridx = 1;
        form.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(styledLabel("Initial Balance ($):"), gbc);
        JTextField balanceField = styledTextField(20);
        gbc.gridx = 1;
        form.add(balanceField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        StyledButton addBtn = new StyledButton("\u2795  Add User", Theme.ACCENT_GREEN, Theme.ACCENT_TEAL, Theme.BG_DARK);
        addBtn.setPreferredSize(new Dimension(200, 42));
        addBtn.setMaximumSize(new Dimension(200, 42));
        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int balance = Integer.parseInt(balanceField.getText().trim());
                if (name.isEmpty()) throw new RuntimeException("Name cannot be empty");
                User user = new User(name, balance);
                service.addUser(user);
                String msg = "User '" + name + "' added with id = " + user.getIdentifier() + ", balance = $" + user.getBalance();
                logSuccess(msg);
                ToastNotification.show(this, msg, ToastNotification.Type.SUCCESS);
                nameField.setText("");
                balanceField.setText("");
            } catch (NumberFormatException ex) {
                logError("Invalid balance. Please enter a number.");
                ToastNotification.show(this, "Invalid balance input", ToastNotification.Type.ERROR);
            } catch (Exception ex) {
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            }
        });
        form.add(addBtn, gbc);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    // ── View Balance Panel ──

    private JPanel createBalancePanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panel.add(styledHeading("\uD83D\uDCCA  View User Balance"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(styledLabel("User ID:"), gbc);
        JTextField idField = styledTextField(20);
        gbc.gridx = 1;
        form.add(idField, gbc);

        // Result display
        RoundedPanel resultCard = new RoundedPanel(Theme.BG_CARD);
        resultCard.setLayout(new BoxLayout(resultCard, BoxLayout.Y_AXIS));
        resultCard.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        resultCard.setVisible(false);

        JLabel resultName = new JLabel("—");
        resultName.setFont(Theme.FONT_HEADING);
        resultName.setForeground(Theme.ACCENT_BLUE);
        resultName.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel resultBalance = new JLabel("—");
        resultBalance.setFont(new Font("Segoe UI", Font.BOLD, 36));
        resultBalance.setForeground(Theme.ACCENT_GREEN);
        resultBalance.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel resultId = new JLabel("—");
        resultId.setFont(Theme.FONT_SMALL);
        resultId.setForeground(Theme.TEXT_DIM);
        resultId.setAlignmentX(Component.LEFT_ALIGNMENT);

        resultCard.add(resultName);
        resultCard.add(Box.createVerticalStrut(5));
        resultCard.add(resultBalance);
        resultCard.add(Box.createVerticalStrut(5));
        resultCard.add(resultId);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        StyledButton searchBtn = new StyledButton("\uD83D\uDD0D  Look Up", Theme.ACCENT_BLUE, Theme.ACCENT_MAUVE, Theme.BG_DARK);
        searchBtn.setPreferredSize(new Dimension(200, 42));
        searchBtn.setMaximumSize(new Dimension(200, 42));
        searchBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                User user = service.getUserById(id);
                resultName.setText("\uD83D\uDC64  " + user.getName());
                resultBalance.setText("$" + user.getBalance());
                resultId.setText("User ID: " + user.getIdentifier());
                resultCard.setVisible(true);
                resultCard.fadeIn();
                logInfo(user.getName() + " — Balance: $" + user.getBalance());
            } catch (UserNotFoundException ex) {
                resultCard.setVisible(false);
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            } catch (NumberFormatException ex) {
                logError("Invalid User ID");
                ToastNotification.show(this, "Invalid User ID", ToastNotification.Type.ERROR);
            }
        });
        form.add(searchBtn, gbc);

        JPanel center = new JPanel(new BorderLayout(0, 20));
        center.setOpaque(false);
        center.add(form, BorderLayout.NORTH);
        center.add(resultCard, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ── Transfer Panel ──

    private JPanel createTransferPanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panel.add(styledHeading("\u21C4  Perform Transfer"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(styledLabel("Sender ID:"), gbc);
        JTextField senderField = styledTextField(20);
        gbc.gridx = 1;
        form.add(senderField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(styledLabel("Recipient ID:"), gbc);
        JTextField recipientField = styledTextField(20);
        gbc.gridx = 1;
        form.add(recipientField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(styledLabel("Amount ($):"), gbc);
        JTextField amountField = styledTextField(20);
        gbc.gridx = 1;
        form.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        StyledButton transferBtn = new StyledButton("\u21C4  Transfer", Theme.ACCENT_MAUVE, Theme.ACCENT_BLUE, Theme.BG_DARK);
        transferBtn.setPreferredSize(new Dimension(200, 42));
        transferBtn.setMaximumSize(new Dimension(200, 42));
        transferBtn.addActionListener(e -> {
            try {
                int senderId = Integer.parseInt(senderField.getText().trim());
                int recipientId = Integer.parseInt(recipientField.getText().trim());
                int amount = Integer.parseInt(amountField.getText().trim());
                service.transferTransaction(senderId, recipientId, amount);
                String msg = "Transfer of $" + amount + " from user " + senderId + " to user " + recipientId + " completed";
                logSuccess(msg);
                ToastNotification.show(this, msg, ToastNotification.Type.SUCCESS);
                senderField.setText("");
                recipientField.setText("");
                amountField.setText("");
            } catch (UserNotFoundException ex) {
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            } catch (IllegalTransactionException ex) {
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            } catch (NumberFormatException ex) {
                logError("Invalid input. Please enter valid numbers.");
                ToastNotification.show(this, "Invalid input", ToastNotification.Type.ERROR);
            } catch (Exception ex) {
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            }
        });
        form.add(transferBtn, gbc);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    // ── View Transactions Panel ──

    private JPanel createViewTxPanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panel.add(styledHeading("\uD83D\uDCCB  User Transactions"), BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        top.setOpaque(false);
        top.add(styledLabel("User ID:"));
        JTextField idField = styledTextField(10);
        top.add(idField);

        StyledButton fetchBtn = new StyledButton("\uD83D\uDD0D  Fetch", Theme.ACCENT_BLUE, Theme.ACCENT_TEAL, Theme.BG_DARK);
        fetchBtn.setPreferredSize(new Dimension(120, 38));
        fetchBtn.setMaximumSize(new Dimension(120, 38));
        top.add(fetchBtn);

        // Transaction list area
        JPanel txListPanel = new JPanel();
        txListPanel.setLayout(new BoxLayout(txListPanel, BoxLayout.Y_AXIS));
        txListPanel.setBackground(Theme.BG_CARD);

        JScrollPane scroll = new JScrollPane(txListPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Theme.BG_CARD);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        customizeScrollBar(scroll);

        fetchBtn.addActionListener(e -> {
            txListPanel.removeAll();
            try {
                int userId = Integer.parseInt(idField.getText().trim());
                Transaction[] txArr = service.getTransactions(userId);
                if (txArr.length == 0) {
                    JLabel noTx = new JLabel("  No transactions found for this user.");
                    noTx.setFont(Theme.FONT_BODY);
                    noTx.setForeground(Theme.TEXT_DIM);
                    txListPanel.add(noTx);
                    logInfo("No transactions found for user " + userId);
                } else {
                    for (int i = 0; i < txArr.length; i++) {
                        txListPanel.add(createTransactionCard(txArr[i], i));
                        txListPanel.add(Box.createVerticalStrut(6));
                    }
                    logInfo("Loaded " + txArr.length + " transaction(s) for user " + userId);
                }
            } catch (UserNotFoundException ex) {
                JLabel errLbl = new JLabel("  " + ex.getMessage());
                errLbl.setForeground(Theme.ACCENT_RED);
                errLbl.setFont(Theme.FONT_BODY);
                txListPanel.add(errLbl);
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            } catch (NumberFormatException ex) {
                logError("Invalid User ID");
                ToastNotification.show(this, "Invalid User ID", ToastNotification.Type.ERROR);
            }
            txListPanel.revalidate();
            txListPanel.repaint();
        });

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setOpaque(false);
        center.add(top, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTransactionCard(Transaction tx, int index) {
        RoundedPanel card = new RoundedPanel(Theme.BG_OVERLAY);
        card.setLayout(new BorderLayout(10, 5));
        card.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        boolean isCredit = tx.getCategory() == TransferCategory.CREDIT;
        Color amountColor = isCredit ? Theme.ACCENT_GREEN : Theme.ACCENT_RED;
        String arrow = isCredit ? "\u2B07 IN" : "\u2B06 OUT";
        User other = isCredit ? tx.getSender() : tx.getRecipient();
        String direction = isCredit
                ? "From " + other.getName() + " (id=" + other.getIdentifier() + ")"
                : "To " + other.getName() + " (id=" + other.getIdentifier() + ")";

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel dirLabel = new JLabel(arrow + "  " + direction);
        dirLabel.setFont(Theme.FONT_BODY);
        dirLabel.setForeground(Theme.TEXT_PRIMARY);

        JLabel idLabel = new JLabel("ID: " + tx.getIdentifier().toString());
        idLabel.setFont(Theme.FONT_SMALL);
        idLabel.setForeground(Theme.TEXT_DIM);

        left.add(dirLabel);
        left.add(Box.createVerticalStrut(3));
        left.add(idLabel);

        JLabel amountLabel = new JLabel("$" + tx.getTransferAmount());
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        amountLabel.setForeground(amountColor);

        card.add(left, BorderLayout.CENTER);
        card.add(amountLabel, BorderLayout.EAST);

        // Staggered fade-in
        Timer delay = new Timer(index * 80, e -> {
            card.fadeIn();
            ((Timer) e.getSource()).stop();
        });
        delay.setRepeats(false);
        delay.start();

        return card;
    }

    // ── Remove Transfer Panel (DEV) ──

    private JPanel createRemoveTxPanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panel.add(styledHeading("\uD83D\uDDD1  Remove Transfer (DEV)"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(styledLabel("User ID:"), gbc);
        JTextField userIdField = styledTextField(20);
        gbc.gridx = 1;
        form.add(userIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(styledLabel("Transaction UUID:"), gbc);
        JTextField txIdField = styledTextField(36);
        gbc.gridx = 1;
        form.add(txIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        StyledButton removeBtn = new StyledButton("\uD83D\uDDD1  Remove", Theme.ACCENT_RED, new Color(200, 100, 120), Theme.BG_DARK);
        removeBtn.setPreferredSize(new Dimension(200, 42));
        removeBtn.setMaximumSize(new Dimension(200, 42));
        removeBtn.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText().trim());
                UUID txId = UUID.fromString(txIdField.getText().trim());
                String result = service.removeTransaction(userId, txId);
                logSuccess(result);
                ToastNotification.show(this, result, ToastNotification.Type.SUCCESS);
                userIdField.setText("");
                txIdField.setText("");
            } catch (UserNotFoundException ex) {
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            } catch (TransactionNotFoundException ex) {
                logError(ex.getMessage());
                ToastNotification.show(this, ex.getMessage(), ToastNotification.Type.ERROR);
            } catch (Exception ex) {
                logError("Invalid input: " + ex.getMessage());
                ToastNotification.show(this, "Invalid input", ToastNotification.Type.ERROR);
            }
        });
        form.add(removeBtn, gbc);

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    // ── Check Validity Panel (DEV) ──

    private JPanel createCheckValPanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_SURFACE);
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        panel.add(styledHeading("\u2714  Check Transfer Validity (DEV)"), BorderLayout.NORTH);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(Theme.BG_CARD);

        JScrollPane scroll = new JScrollPane(resultPanel);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Theme.BG_CARD);
        customizeScrollBar(scroll);

        StyledButton checkBtn = new StyledButton("\u2714  Run Check", Theme.ACCENT_YELLOW, Theme.ACCENT_PEACH, Theme.BG_DARK);
        checkBtn.setPreferredSize(new Dimension(200, 42));
        checkBtn.setMaximumSize(new Dimension(200, 42));
        checkBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkBtn.addActionListener(e -> {
            resultPanel.removeAll();
            try {
                Transaction[] unpaired = service.checkValidityOfTransactions();
                if (unpaired.length == 0) {
                    JLabel ok = new JLabel("  \u2713  All transactions are properly paired!");
                    ok.setFont(Theme.FONT_HEADING);
                    ok.setForeground(Theme.ACCENT_GREEN);
                    resultPanel.add(ok);
                    logSuccess("All transactions are properly paired");
                    ToastNotification.show(this, "All transactions valid!", ToastNotification.Type.SUCCESS);
                } else {
                    for (int i = 0; i < unpaired.length; i++) {
                        Transaction t = unpaired[i];
                        User recipient = t.getRecipient();
                        User sender = t.getSender();
                        String msg = recipient.getName() + " (id=" + recipient.getIdentifier()
                                + ") has unacknowledged transfer id=" + t.getIdentifier()
                                + " from " + sender.getName() + " (id=" + sender.getIdentifier()
                                + ") for $" + t.getTransferAmount();

                        RoundedPanel card = new RoundedPanel(Theme.BG_OVERLAY);
                        card.setLayout(new BorderLayout());
                        card.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
                        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                        JLabel warnIcon = new JLabel("\u26A0  ");
                        warnIcon.setFont(Theme.FONT_HEADING);
                        warnIcon.setForeground(Theme.ACCENT_YELLOW);

                        JLabel msgLabel = new JLabel("<html><body style='width:500px'>" + msg + "</body></html>");
                        msgLabel.setFont(Theme.FONT_SMALL);
                        msgLabel.setForeground(Theme.TEXT_PRIMARY);

                        card.add(warnIcon, BorderLayout.WEST);
                        card.add(msgLabel, BorderLayout.CENTER);

                        resultPanel.add(card);
                        resultPanel.add(Box.createVerticalStrut(6));
                        logWarning(msg);
                    }
                    ToastNotification.show(this, unpaired.length + " unpaired transaction(s) found", ToastNotification.Type.WARNING);
                }
            } catch (Exception ex) {
                logError(ex.getMessage());
            }
            resultPanel.revalidate();
            resultPanel.repaint();
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        top.add(checkBtn);

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setOpaque(false);
        center.add(top, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);

        panel.add(center, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════ LOG PANEL ═══════════════════════════

    private JPanel createLogPanel() {
        RoundedPanel logPanel = new RoundedPanel(Theme.BG_SURFACE);
        logPanel.setLayout(new BorderLayout(5, 5));
        logPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        logPanel.setPreferredSize(new Dimension(0, 140));

        JLabel logTitle = new JLabel("\uD83D\uDCDD  Activity Log");
        logTitle.setFont(Theme.FONT_BTN);
        logTitle.setForeground(Theme.TEXT_DIM);

        logPane = new JTextPane();
        logPane.setEditable(false);
        logPane.setBackground(Theme.BG_CARD);
        logPane.setForeground(Theme.TEXT_PRIMARY);
        logPane.setFont(Theme.FONT_MONO);
        logPane.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        logDoc = logPane.getStyledDocument();

        JScrollPane logScroll = new JScrollPane(logPane);
        logScroll.setBorder(BorderFactory.createEmptyBorder());
        logScroll.getViewport().setBackground(Theme.BG_CARD);
        customizeScrollBar(logScroll);

        StyledButton clearBtn = new StyledButton("Clear", Theme.BG_OVERLAY, Theme.BTN_HOVER, Theme.TEXT_DIM);
        clearBtn.setPreferredSize(new Dimension(70, 28));
        clearBtn.setMaximumSize(new Dimension(70, 28));
        clearBtn.addActionListener(e -> {
            try { logDoc.remove(0, logDoc.getLength()); } catch (Exception ignored) {}
        });

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(logTitle, BorderLayout.WEST);
        header.add(clearBtn, BorderLayout.EAST);

        logPanel.add(header, BorderLayout.NORTH);
        logPanel.add(logScroll, BorderLayout.CENTER);
        return logPanel;
    }

    private void appendLog(String text, Color color) {
        SwingUtilities.invokeLater(() -> {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, color);
            StyleConstants.setFontFamily(attrs, "JetBrains Mono");
            StyleConstants.setFontSize(attrs, 13);
            try {
                logDoc.insertString(logDoc.getLength(), text + "\n", attrs);
                logPane.setCaretPosition(logDoc.getLength());
            } catch (Exception ignored) {}
        });
    }

    private void logSuccess(String msg) { appendLog("\u2713 " + msg, Theme.ACCENT_GREEN); }
    private void logError(String msg)   { appendLog("\u2717 " + msg, Theme.ACCENT_RED); }
    private void logInfo(String msg)    { appendLog("\u2139 " + msg, Theme.ACCENT_BLUE); }
    private void logWarning(String msg) { appendLog("\u26A0 " + msg, Theme.ACCENT_YELLOW); }

    // ═══════════════════════════ HELPERS ═══════════════════════════

    private JLabel styledHeading(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_TITLE);
        label.setForeground(Theme.TEXT_PRIMARY);
        return label;
    }

    private JLabel styledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_BODY);
        label.setForeground(Theme.TEXT_SECONDARY);
        return label;
    }

    private JTextField styledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(Theme.FONT_BODY);
        field.setBackground(Theme.BG_OVERLAY);
        field.setForeground(Theme.TEXT_PRIMARY);
        field.setCaretColor(Theme.ACCENT_BLUE);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BG_OVERLAY, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Theme.ACCENT_BLUE, 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Theme.BG_OVERLAY, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });
        return field;
    }

    private void customizeScrollBar(JScrollPane scroll) {
        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Theme.BG_OVERLAY;
                this.trackColor = Theme.BG_CARD;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return zeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return zeroButton();
            }

            private JButton zeroButton() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0, 0));
                return btn;
            }
        });
    }
}
