#pragma once

#include <WS2tcpip.h>
#include <iostream>
#include <string>

#pragma comment(lib, "Ws2_32.lib")

class BindShell
{
public:
	BindShell();
	~BindShell();

	int bindShell(unsigned short port);
};
