SetOpLock

(c) Google Inc. 2015

Coded by James Forshaw. 

This is a simple tool to create an arbitrary DosDevices symlink using DefineDosDevice API. This is different from subst as you
can specify an arbitrary symlink path.

Basic usage:

CreateDosDeviceSymlink [-d] symlink\path [target]

The symlink path can be absolute (for example \RPC Control\abc, or relative to the user's DosDevices directory). The target is
optional if you're deleting the symlink with the -d command. Note that you must delete the symlink the same number of times as
you created it otherwise it persists. 

