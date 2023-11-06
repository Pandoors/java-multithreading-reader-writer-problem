1) Readers-Writers classic multi-threading problem - definition

Readers-Writers problem is a typical Java algorithm solved using multi-threading.
Both readers and writers are trying to access a resource, a library, at the same moment.
While readers are given the access to the resource, the writers must wait. When library is free
of Readers, one Writer may access the resource. During that time no other writer nor Reader may enter
the room. It is being implemented using threads and multithreading. Every writer and reader are created
in separate Threads and are accessing the same Library class instance. The Library is managing the access
to the resource. Each Reader's and Writer's action is being verified by the Library and being printed on the
Standard Output according to the instructions given by the Professor.

2) How to start the program

To start the program first You need to generate the .jar file, by running `mvn clean package`. Than run the jar from
main module located in target directory. Subsequently watch the Standard Output where the program will inform You
who is currently in the library.

3) Communication protocol

Reader/Writer instance is created -> Server is printing specific log on Standard Output.
Reader/Writer enters a Library -> Server is printing specific log on Standard Output with the number of current Users
in Library.
Reader/Writer leaves a Library -> Server is printing specific log on Standard Output with the number of current Users
in Library.
Take note that the Program won't stop running by itself. It must be stopped "by force".
Otherwise it will carry on working which is done on purpose.
Client doesn't have to write anything to the Standard Input or anywhere else.


4) Additional info

Because there are much more instances of Reader class rather than Writer and there may be 5 readers at a time in library
who can theoretically, than, after leaving the library try to access it once again if the conditions are fulfilled - the
Writer may begin to starve and be very rarely given the resource access.
My implementation of Reader-Writer algorithm is ensuring that the Writer won't be starved to death. It is being done
with usage of a HashMap. After all Readers are created - the Map <UserId, boolean> is created. First - all keys
values are set to false. If Reader enters and leaves the library its value in Map is set to true. Furthermore it cannot
access the resource again unless writer enters and leaves the library. When that happens all Map values are again set
to false and Readers can access the resource once again.