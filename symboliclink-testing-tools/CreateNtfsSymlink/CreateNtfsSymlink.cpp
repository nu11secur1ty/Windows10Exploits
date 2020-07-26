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
#include <ReparsePoint.h>
#include <CommonUtils.h>

int _tmain(int argc, _TCHAR* argv[])
{
	std::vector<WCHAR*> args(&argv[1], &argv[argc]);
	bool directory = false;
	bool relative = false;
	LPCWSTR name = nullptr;
	LPCWSTR target = nullptr;
	LPCWSTR printname = nullptr;
	int argcount = 0;

	for (auto i = args.begin(); i != args.end(); ++i)
	{
		WCHAR* curr = *i;

		if (curr[0] == '-')
		{
			switch (curr[1])
			{
			case 'd':
				directory = true;
				break;
			case 'r':
				relative = true;
				break;
			}			
		}
		else
		{
			break;
		}
		argcount++;
	}

	args.erase(args.begin(), args.begin() + argcount);

	if (args.size() < 2)
	{
		printf("CreateNtfsSymlink [-r] [-d] name target [printname]\n");
		return 1;
	}

	name = args[0];
	target = args[1];
	if (args.size() > 2)
	{
		printname = args[2];
	}
	else
	{
		printname = target;
	}

	HANDLE hToken = nullptr;
	BOOL bRestore = FALSE;

	OpenProcessToken(GetCurrentProcess(), TOKEN_ALL_ACCESS, &hToken);

	if (!SetPrivilege(hToken, SE_CREATE_SYMBOLIC_LINK_NAME, TRUE))
	{
		if (!SetPrivilege(hToken, SE_RESTORE_NAME, TRUE))
		{
			printf("Couldn't enable an appropriate privilege\n");
		}
		else
		{
			bRestore = TRUE;
		}
	}

	HANDLE hFile;
	
	if (directory)
	{
		CreateDirectory(name, nullptr);
		hFile = CreateFile(name, GENERIC_READ | GENERIC_WRITE, 0, nullptr,
			OPEN_EXISTING, FILE_FLAG_OPEN_REPARSE_POINT | FILE_FLAG_BACKUP_SEMANTICS, nullptr);
	}
	else
	{
		hFile = CreateFile(name, GENERIC_READ | GENERIC_WRITE, 0, nullptr,
			CREATE_ALWAYS, FILE_FLAG_OPEN_REPARSE_POINT | (bRestore ? FILE_FLAG_BACKUP_SEMANTICS : 0), nullptr);
	}

	if (hFile != INVALID_HANDLE_VALUE)
	{				
		if (!ReparsePoint::CreateSymlink(hFile, target, printname, relative))
		{
			printf("Error creating symlink - %ls\n", GetErrorMessage().c_str());
		}

		CloseHandle(hFile);
	}
	else
	{
		printf("Error creating symlink file - %ls\n", GetErrorMessage().c_str());
	}

	return 0;
}

