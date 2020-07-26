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
#include <FileOpLock.h>

static FileOpLock* oplock = nullptr;
static FileSymlink* sl = nullptr;
static bstr_t target_2;

void SwitchSymlink()
{
	printf("Switching symlink to %ls\n", target_2.GetBSTR());
	sl->ChangeSymlink(target_2);
}

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 3)
	{
		printf("Usage: BaitAndSwitch symlink target1 target2 [rwdx]\n");
    printf("Share Mode:\n");
    printf("r - FILE_SHARE_READ\n");
    printf("w - FILE_SHARE_WRITE\n");
    printf("d - FILE_SHARE_DELETE\n");
    printf("x - Exclusive lock\n");
		return 1;
	}
	else
	{
		LPCWSTR symlink = argv[1];
		LPCWSTR target = argv[2];
		target_2 = argv[3];		
		LPCWSTR share_mode = argc > 4 ? argv[4] : L"";

		sl = new FileSymlink();

		if (sl->CreateSymlink(argv[1], argv[2], nullptr))
		{
			oplock = FileOpLock::CreateLock(target, share_mode, SwitchSymlink);
			if (oplock != nullptr)
			{
				DebugPrintf("Press ENTER to exit and delete the symlink\n");
				getc(stdin);
				
				delete oplock;
			}

			delete sl;
			sl = nullptr;
			
			return 0;
		}
		else
		{
			return 1;
		}
	}

	return 0;
}

