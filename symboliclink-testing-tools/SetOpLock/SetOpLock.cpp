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

static FileOpLock* oplock = nullptr;
static bstr_t target_2;

void HandleOplock()
{
	DebugPrintf("OpLock triggered, hit ENTER to close oplock\n");
	getc(stdin);
}

int _tmain(int argc, _TCHAR* argv[])
{	
	if (argc < 2)
	{
		printf("Usage: SetOpLock target [rwdx]\n");
    printf("Share Mode:\n");
    printf("r - FILE_SHARE_READ\n");
    printf("w - FILE_SHARE_WRITE\n");
    printf("d - FILE_SHARE_DELETE\n");
    printf("x - Exclusive lock\n");
		return 1;
	}
	else
	{				
		LPCWSTR target = argv[1];
		LPCWSTR share_mode = argc > 2 ? argv[2] : L"";

		oplock = FileOpLock::CreateLock(target, share_mode, HandleOplock);
		if (oplock != nullptr)
		{
			oplock->WaitForLock(INFINITE);

			delete oplock;
		}			
		else
		{
			printf("Error creating oplock\n");
			return 1;
		}
	}

	return 0;
}

