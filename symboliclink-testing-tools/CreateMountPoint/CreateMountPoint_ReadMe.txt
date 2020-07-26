CreateMountPoint

Coded by James Forshaw. 

This is a simple tool to create mount point (sometimes known as junctions or reparse points). This improves
on the inbuilt MKLINK tool as you can specify arbitrary junction paths easily.

Basic usage:

CreateMountPoint path target [printname]
Printname is optional, it is displayed instead of the real target path (see a dir listing in cmd). 