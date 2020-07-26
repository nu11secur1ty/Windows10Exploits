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
#include <CommonUtils.h>

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 2)
	{
		printf("DeleteReparsePoint directory [-d]\n");
		printf("Specify -d to delete the underlying directory\n");
		return 1;
	}
	else
	{
		if (!ReparsePoint::DeleteMountPoint(argv[1]))
		{
			printf("Error deleting mount point %ls\n", GetErrorMessage().c_str());
			return 1;
		}

		if ((argc > 2) && (wcscmp(argv[2], L"-d") == 0))
		{
			RemoveDirectory(argv[1]);
		}
	}

	return 0;
}

