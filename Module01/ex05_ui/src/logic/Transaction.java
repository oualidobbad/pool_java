package logic;

import java.util.UUID;

public class Transaction {
    private UUID identifier;
    private User recipient;
    private User sender;
    private TransferCategory category;
    private Integer transferAmount;

    public Transaction(User sender, User recipient, TransferCategory category, Integer transferAmount) {
        identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferAmount = 0;
        this.category = category;

        if (category == TransferCategory.DEBIT) {
            if (transferAmount >= 0) {
                System.err.println("Error: DEBIT amount must be negative.");
                return;
            }
            this.transferAmount = transferAmount;
        } else {
            if (transferAmount < 0) {
                System.err.println("Error: CREDIT amount must be positive.");
                return;
            }
            this.transferAmount = transferAmount;
        }
    }

    public TransferCategory getCategory() {
        return category;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        if (category == TransferCategory.DEBIT) {
            if (transferAmount >= 0) {
                System.err.println("Error: DEBIT amount must be negative.");
                return;
            }
            this.transferAmount = transferAmount;
        } else {
            if (transferAmount < 0) {
                System.err.println("Error: CREDIT amount must be positive.");
                return;
            }
            this.transferAmount = transferAmount;
        }
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "Transaction{id=" + identifier + ", sender=" + sender.getName()
                + ", recipient=" + recipient.getName() + ", category=" + category
                + ", amount=$" + transferAmount + "}";
    }
}
