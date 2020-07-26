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

void Test();

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 3)
	{
		printf("CreateNativeSymlink symlink target\n");
		printf("Example: \\ABC\\XYZ \\??\\c:\n");
	}
	else
	{
		LPCWSTR symlink = argv[1];
		LPCWSTR target = argv[2];

		HANDLE hSymlink = CreateSymlink(nullptr, symlink, target);
		
		if(hSymlink)
		{
			printf("Press ENTER to exit and delete the symlink\n");
			getc(stdin);
			return 0;
		}
		else
		{
			printf("Error creating symlink: %ls\n", GetErrorMessage().c_str());
			return 1;
		}
	}

	return 0;
}

