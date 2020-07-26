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
	if (argc < 2)
	{
		printf("DumpReparsePoint path\n");
		return 1;
	}	

	std::wstring path = argv[1];

	if (ReparsePoint::IsReparsePoint(path))
	{
		if (ReparsePoint::IsMountPoint(path))
		{
			std::wstring target;
			std::wstring printname;

			printf("Mount Point\n");

			if (ReparsePoint::ReadMountPoint(path, target, printname))
			{
				printf("Target: %ls\n", target.c_str());
				printf("Print Name: %ls\n", printname.c_str());
			}
		}
		else if (ReparsePoint::IsSymlink(path))
		{
			printf("Symlink\n");

			std::wstring target;
			std::wstring printname;
			unsigned int flags;

			if (ReparsePoint::ReadSymlink(path, target, printname, &flags))
			{
				printf("Target: %ls\n", target.c_str());
				printf("Print Name: %ls\n", printname.c_str());
				if (flags == 1)
				{
					printf("Relative Symlink\n");
				}
				else
				{
					printf("Flags: %08X\n", flags);
				}
			}
		}
		else
		{
			printf("Generic\n");
			std::vector<BYTE> buffer;
			unsigned int reparse_tag;

			if (ReparsePoint::ReadRaw(path, &reparse_tag, buffer))
			{
				printf("Reparse Tag: %08X\n", reparse_tag);
				if (buffer.size() > 0)
				{
					for (size_t i = 0; i < buffer.size(); i += 16)
					{
						printf("%08X: ", i);
						for (size_t j = 0; j < 16; ++j)
						{
							if (i + j < buffer.size())
							{
								printf("%02X ", buffer[i + j]);
							}
							else
							{
								printf("   ");
							}
						}

						printf(" - ");

						for (size_t j = 0; j < 16; ++j)
						{
							if (i + j < buffer.size())
							{
								char c = buffer[i + j];

								if (isprint(c))
								{
									printf("%c", c);
								}
								else
								{
									printf(".");
								}
							}
							else
							{
								printf(" ");
							}
						}

						printf("\n");
					}
				}
			}
		}
	}

	return 0;
}

