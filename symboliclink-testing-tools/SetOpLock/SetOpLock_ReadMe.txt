SetOpLock

(c) Google Inc. 2015

Coded by James Forshaw. 

This is a simple tool to set an oplock on a file or directory and then wait for user confirmation to release it.

Basic usage:

SetOpLock c:\path\to\file [sharemode]


The optional sharemode specifies what types of share access will trigger the oplock handling. You can specify r (read), w (write), 
d (delete) or a combination of these. If you don't specify any mode then the file will be opened with exclusive access. This affects
the share mode which is permitted by the other application opening it and not the type of file access. 

Caveats and Notes:

OpLocks only work on file streams, that is to say the file/directory must be opened with access rights which allow the access to the 
file's data stream. E.g. FILE_APPEND_DATA will trigger a write request, however READ_CONTROL or WRITE_DACL will not trigger the oplock
as that is related to the attribute data of the file not the file's data itself. This can be good and bad, it allows you to automatically
ignore anything related to just enumerating file information. On the other hand if that's the only triggerable signals prior to the use 
of the file you're out of luck. In certain circumstances it might be possible to oplock on higher directories (querying a directory for
it's contents is a read request) especially if code is using something like GetLongPathName API. This trick might only work on Win8. 

The actual built version won't run on anything prior to Windows 7, however it could be rebuilt for as far back as 2000 and it certainly
works on XP. 
