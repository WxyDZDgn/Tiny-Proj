# Ethernet Simulation

## Overall

This is a project, which can simulate the frame spreading in the ethernet. By creating the net and send frame from one
host to another, we can easily get to know about the switches' procession.

The ethernet switches have some features. They all have a table to restore one host's MAC address, in this case, _id_,
known as the _key_, as well as their specific port numbers for every _key_, as the _value_.

If one switch received a frame, it will write the poster's MAC address (or _id_ in this project) with specific port
number. Then, if it doesn't know where the destination is, it will broadcast this frame to every port. Otherwise, it
only sends the frame to a specific port where the target is.

As for the hosts, if a frame's destination is not in its place, the host will just reject it. On the contrary, if one is
received the right frame, it'll accept it.

## Details

Main class can be run, which is interactive. It'll show the frame's adventure throughout the entire net with user's
usage.

Host class is for the hosts where the frames are posted and received.

Switch class is for the switches in the ethernet, which have a table, restoring the address and its port number.

Message class (actually, it's Frame class) is for the frames needed to be transferred from one host to another, with
the sender's id and receiver's id inside.

## Others

As it is just one simulation about ethernet, there are some simplifications. That's why this project might not be
_correct_. However, there is something that we can get to know. So that's not too bad.
