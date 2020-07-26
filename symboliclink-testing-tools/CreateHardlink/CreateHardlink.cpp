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

int _tmain(int argc, _TCHAR* argv[])
{
	if (argc < 3)
	{
		printf("CreateHardLink hardlink target\n");
		printf("Example: hello.txt goodbye.txt\n");
		return 1;
	}
	else
	{
		if (CreateNativeHardlink(argv[1], argv[2]))
		{
			printf("Done\n");
		}
		else
		{
			printf("Error creating hardlink: %ls\n", GetErrorMessage().c_str());
			return 1;
		}
	}

	return 0;
}

