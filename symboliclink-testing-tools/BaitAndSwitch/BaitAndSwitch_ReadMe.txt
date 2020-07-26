BaitAndSwitch

(c) Google Inc. 2015

Coded by James Forshaw. 

This is a simple tool to perform a TOCTOU trick against an NTFS file. It uses OPLOCKs to catch when a process wants to open a file
and switches a symbolic link out from under it.

Basic usage:

BaitAndSwitch c:\path\to\link target_1 target_2 [sharemode]

The optional sharemode specifies what types of share access will trigger the oplock handling. You can specify r (read), w (write), 
d (delete) or a combination of these. If you don't specify any mode then the file will be opened with exclusive access. This affects
the share mode which is permitted by the other application opening it and not the type of file access. 

What can you do?:

This tool allows you to wait on access to a file through a symbolic link then switch the symlink before the caller gets access. An
example of where this might be useful is in exploiting TOCTOU bugs, for example a privileged application which verifies a file has
a specific signature before re-opening. As the tool uses symlinks certain defensive patterns such as placing a write lock on a file
can't work block the attack. There are many variations of this attack though, not just this one so it's more of a demo tool.

To demonstrate the issue setup the bait and switch on a file then run the following from the command line:

more < path\to\file
more < path\to\file

You should expect to see two different responses (assuming your files are different).

Caveats and Notes:

You can only create symlinks in directories which are empty, this is a limitation on the procesing of junction points, so you can't just
drop into %TEMP%. However if you can at least delete the files from the directory you should still be able to do it.

As the object manager namespace isn't a filesystem the calls to FindFirstFile on the object directory (i.e. the target of the junction)
will fail. This can be a problem when an application tries to find the file before using it. However direct calls such as CreateFile or
GetFileInformation should work as expected. Another issue you might encounter is applications calling GetLongPathName which will attmept
to do FindFirstFile in order to discover the real names. This can usually be worked around by ensuring the linkname is not a valid 8.3
name. This will cause the processing code to assume it's already a long filename and so not call FindFirstFile.

The object manager symbolic links only last until all referencing handles have been closed, this is because it's not possible to specify
OBJ_PERMANENT flag to the creation routine without admin privileges.  

OpLocks only work on file streams, that is to say the file/directory must be opened with access rights which allow the access to the 
file's data stream. E.g. FILE_APPEND_DATA will trigger a write request, however READ_CONTROL or WRITE_DACL will not trigger the oplock
as that is related to the attribute data of the file not the file's data itself. This can be good and bad, it allows you to automatically
ignore anything related to just enumerating file information. On the other hand if that's the only triggerable signals prior to the use 
of the file you're out of luck. In certain circumstances it might be possible to oplock on higher directories (querying a directory for
it's contents is a read request) especially if code is using something like GetLongPathName API. This trick might only work on Win8. 

The actual built version won't run on anything prior to Windows 7, however it could be rebuilt for as far back as 2000 and it certainly
works on XP. 
