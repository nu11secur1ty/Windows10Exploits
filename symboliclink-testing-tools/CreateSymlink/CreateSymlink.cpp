//  Copyright 2015 Google Inc. All Rights Reserved.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http ://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

#include "stdafx.h"
#include <FileSymlink.h>

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 3)
	{
		printf("CreateSymlink [-p] symlink target [baseobjdir]\n");
		printf("Example: C:\\path\\file c:\\otherpath\\otherfile\n");		
	}
	else
	{
		bool permanent = false;
		int arg_start = 1;

		if (wcscmp(argv[1], L"-p") == 0)
		{
			permanent = true;
			arg_start = 2;
		}

		LPCWSTR symlink = argv[arg_start];
		LPCWSTR target = argv[arg_start + 1];
		LPCWSTR baseobjdir = nullptr;
		if (argc - arg_start > 2)
		{
			baseobjdir = argv[arg_start + 2];
		}

		FileSymlink sl(permanent);

		if (sl.CreateSymlink(symlink, target, baseobjdir))
		{
			if (!permanent)
			{
				DebugPrintf("Press ENTER to exit and delete the symlink\n");
				getc(stdin);				
			}
		}
		else
		{
			return 1;
		}
	}
	
	return 0;
}

