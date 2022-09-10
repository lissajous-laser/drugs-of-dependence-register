# Drugs of Dependence Register

## Background
Under the Drugs, Poisons and Controlled Substances Regulations 2017, pharmacists and other health professionals are required to keep records of transactions for all Schedule 8 (S8) drugs. S8 drugs are also known as controlled drugs, or drugs of dependence. They have a theraputic use (i.e. are useful medicines), but they are at high risk of abuse and dependence.

Records kept for these transactions cannot be altered or deleted, and are readily sorted by drug. Each recorded transaction must show a true and accurate balance of drug remaining after the transaction.

An example of a software record is DDBook, by [Modeus](https://www.modeus.com.au/).

## Implementation
The records are stored in an SQLite database , and GUI is created using JavaFX. The database interfaces with the program using JDBC.

## Use

Before entering a supply to a patient, entries for the patient, drug, prescriber and supplying pharmacist need to be entered first. These are accessible from the main screen.
![Picture of Main Window](/MarkdownImages/MainWindow.png)

Because entering new patients and operations are a more common operation, they are also accessible with the '+' labelled buttons in the Supply to Patient Window. When entering in a supply, the patient, drug, prescriber and pharmacist are chosen with drop-down boxes. There are search fields to filter through these options.
![Picture of Supply to Patient Window](/MarkdownImages/SupplyToPatientWindow.jpg)

A similar procedure is followed for entering in S8 drugs received from the supplier.

Records can be searched by drug with a date range, or by day. Because entries cannot be deleted, a 'reverse entry' function allows a user to create a new entry which reverses the effect of a previous entry by swapping the quantities coming and gong out. This is requied in case of a mistake in an entry, or if a dispensing is cancelled.
![Picture of Search by Drug Window](/MarkdownImages/SearchByDrugWindow.jpg)

A user user will be prompted to write a note about the reversal of the entry.
![Picture of Reverse Entry Window](/MarkdownImages/ReverseEntryWindow.png)
