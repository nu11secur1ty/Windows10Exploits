Create Symbolic Link tool for Windows 2000+

(c) Google Inc. 2015

Coded by James Forshaw. 

This is a simple tool to create file system level "file" symbolic links for the purposes of exploiting privileged services and the like. 
The reason this tool exists when Vista+ already has file level symbolic links is it doesn't require administrator privileges to create,
it only needs access to a location in the object manager's namespace (which even for many sandboxed applications is possible) as well
as the ability to create a directory junction. It can be used to point to directories as well where it has a couple of slightly advantages
over traditional junctions. 

Basic usage:

CreateSymlink [-p] c:\path\to\link c:\targetfile

Both target link and target can starts with a '@" and it will be interpreted verbatim as an absolute native path. If you pass the -p
flag then it will create a permanent symlink which should last until the next reboot. 

What can you do?:

The most obvious is you can exploit TOCTOU attacks or fixed file name dropping bugs from higher privileged services and redirect the 
request to any native object path (or even to things like named pipes). 

Another trick is you can create file paths with obviously invalid characters such as ", ?, *, or /. This can be used to confuse path
canonicalization code and the like. For example you can construct a path which can be opened using standard CreateFile calls but 
contains double quotes which you can use to break out of command line quoting. 

Caveats and Notes:

You don't need to be able to create directory junctions if the application you're exploiting can accept raw NT paths. E.g. you can setup
a symlink in the Object manager and use the syntax \\?\GLOBALROOT\RPC Control\symlinkname. You can create just a native symlink by 
prefixing the linkname with a '@'.

You can only create symlinks in directories which are empty, this is a limitation on the procesing of junction points, so you can't just
drop into %TEMP%. However if you can at least delete the files from the directory you should still be able to do it. 

As the object manager namespace isn't a filesystem the calls to FindFirstFile on the object directory (i.e. the target of the junction)
will fail. This can be a problem when an application tries to find the file before using it. However direct calls such as CreateFile or
GetFileInformation should work as expected. Another issue you might encounter is applications calling GetLongPathName which will attmept
to do FindFirstFile in order to discover the real names. This can usually be worked around by ensuring the linkname is not a valid 8.3
name. This will cause the processing code to assume it's already a long filename and so not call FindFirstFile.

The object manager symbolic links only last until all referencing handles have been closed, this is because it's not possible to specify
OBJ_PERMANENT flag to the creation routine without admin privileges.  

One other weird abuse is it's possible to point a directory junction at a file and it can be opened as a file as long as the caller 
specifies FILE_FLAG_BACKUP_SEMANTICS. This seems to be just strangeness with how junctions are implemented. 

The actual built version won't run on anything prior to Windows 7, however it could be rebuilt for as far back as 2000 and it certainly
works on XP. 