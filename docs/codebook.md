# Pharmascope Codebook 
A description of the database structure for Pharmascope. The database consists of five tables: pt_info, order_info, master_location, history, and security. Each table has a unique row identifier labeled 'ID' as the left-most column.

## Table: pt_info
A table containing information about the patient.
+ Name: name of the patient
+ OrderNum: the unique numerical identifier (number) of the doctor's order. Different orders separated by a semi-colon (;)
+ RoomNum: the room number of the patient and ultimate destination of the drug

## Table: order_info
A table containing information about the doctor's order.
+ OrderNum: the unique numerical identifier (number) of the doctor's order. Different orders separated by a semi-colon (;)
+ NDC: the NDC code that identifies the drug and its dose
+ Quantity: number of pills in the doctor's order
+ UID: a unique numerical identifier for the individual unit dose labeled by the NFC tag

## Table: master_location
An up-to-date table on the location of each unit dose dispensed. The number of rows does not exceed the number of unit doses dispensed.
+ NDC: the NDC code that identifies the drug and its dose
+ UID: a unique numerical identifier for the individual unit dose labeled by the NFC tag
+ Location: the current location of the unit dose dispensed
+ TimeStamp: the time the unit dose was scanned by a scanner

## Table: history
A running list of where each unit dose has been. This table grows as more unit doses are dispensed and as each unit dose is scanned. Doses that have been consumed do not reappear. Maintained primarily for analytical purposes.
+ NDC: the NDC code that identifies the drug and its dose
+ UID: a unique numerical identifier for the individual unit dose labeled by the NFC tag
+ Location: the current location of the unit dose dispensed
+ TimeStamp: the time the unit dose was scanned by a scanner

## Table: security
An ecryption table to hide the NDC codes of prescribed medications. For patient privacy and loss prevention.
+ NDC: the NDC code that identifies the drug and its dose
+ MedName: the tecnical name for the medication
+ HashCode: a md5 hash code generated from the NDC string using [this](https://www.md5hashgenerator.com/) website.
