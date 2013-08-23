Addition
========

The calculator performs basic addition.

| Scenario     | X            | Y            | X + Y      |
|--------------|-------------:|-------------:|-----------:|
| simple add   | 1            | 2            | 3          |
| zero left    | 0            | 2            | 2          |
| zero right   | 1            | 0            | 1          |
| zero both    | 0            | 0            | 0          |


Addition supports negative numbers.

| Scenario                  | X            | Y            | X + Y      |
|---------------------------|-------------:|-------------:|-----------:|
| negative left             | \-4          | 2            | \-2        |
| negative right            | 5            | \-4          | 1          |
| negative left and zero    | \-1          | 0            | \-1        |
| zero and negative right   | 0            | \-6          | \-6        |
| both negative             | \-4          | \-9          | \-13       |


Addition can result in integers larger than 32-bits in size.

| Scenario                 | X             | Y             | X + Y        |
|--------------------------|--------------:|--------------:|-------------:|
| large addition           | 2147483647    | 2147483647    | 4294967294   |
| large negative numbers   | \-2147483648  | \-2147483648  | \-4294967296 |
