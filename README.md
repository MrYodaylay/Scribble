# Scribble

Processing a popular and free programming language, graphics library and IDE. It is designed to teach the fundamentals
of computer programming in an easy-to-use and visual way.

Scribble is a tool for testing and auto-marking Processing sketches.

### Usage

##### Prerequisites
* Java Development Kit (JDK) 17 or above.
* Processing 4.0 or above.
    * The path to your Processing install directory must be on your path.

##### Command Line
`java -jar Scribble.jar [COMMAND LINE ARGUMENTS]`

Where valid command line arguments are:
* `--silent` There is no output to the terminal.
* `--quiet` Only warning or higher level messages are output to the terminal.
* `--verbose` All messages, inlcuding debug messages, are output to the terminal.
* `--sketch` Specifies a path to a single sketch. A valid sketch is a directory containing one or more .pde files.
* `--sketchbook` Specifies a path to a directory of valid sketches. A valid sketch is a subdirectory containing 
one or more .pde files.
* `--testfile` Specifies the path to the test file. A test file is a plaintext .java file.
* `--outfile` Specifies the location of the output files.
* `--format` Specifies the format of the outfile. Valid options are `CSV`, `HTML` and `STD`. The terminal (STD) is the default.

### License
Scribble is licensed under the Mozilla Public License, v. 2.0. All source code in this repository is subject to the
terms of the MPL. If a copy of the MPL was not distributed with this file, you can obtain one at 
https://mozilla.org/MPL/2.0/.