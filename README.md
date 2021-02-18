# APRIORI AND ASSOCIATION RULES

The objective of this project is to build a **terminal-based** application, being capable of the following

* Search for frequent itemsets in a given data set (it's an implementation of the **apriori algorithm**)
* Generate association rules between those frequent itemsets.

Please follow the steps below so as to be able to **install**, **uninstall**, and **reinstall** this application.

## Dependencies

* **Java**, please install java via your package manager

Open your terminal and run the following commands

## GETTING THE PROJECT

Fetch the project
```shell
git clone https://github.com/kuttix/DataMiningAssociationRules.git
```
Enter the directory containing the project
```shell
cd DataMiningAssociationRules
```
There is a *script file* named **akg** needed to perform the rest of the operations

## Installation
```shell
./akg --install
```
## Uninstallation
```shell
./akg --uninstall
```
## Reinstallation
```shell
./akg --reinstall
```

# HOW TO USE

## What to know

Before using the application, you need to know the specifications of the data set.

* The data set should be a text file
* Transactions should be arrange in the following
* For each line in the data set, ```field-1 ........ field-n``` are optional since they are
informations about the item in that transaction. An example of what one of those fields can be 
is the date that item was sold.

```
transaction_id;item;field-1;field-2; ... ;field-n

transaction-1;item_11;field-1;field-2; ...  ;field-n
transaction-1;item_12;field-1;field-2; ...  ;field-n
transaction-2;item_21;field-1;field-2; ...  ;field-n
transaction-2;item_22;field-1;field-2; ...  ;field-n
transaction-2;item_23;field-1;field-2; ...  ;field-n
transaction-3;item_31;filed-1;filed-2; ...  ;field-n
```
From this example, the items of ```transaction-1``` are ```item_11``` and ```item_12```, that of
```transaction-2``` are ```item_21```, ```item_22```, and ```item_23```.

## How to run

After installation, the program  named `asrules` can be accessible at the command line 
provided that ```${HOME}/.local/bin``` is contained by your path variable, if not then 
move to ```${HOME}/.local/bin``` in order to run it.

### Run
```shell
asrules [PATH/TO/THE/DATASET]
```

### Help
```shell
asrules --help
```

