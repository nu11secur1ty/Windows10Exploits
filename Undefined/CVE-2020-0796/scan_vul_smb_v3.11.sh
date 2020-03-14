#!/bin/bash
if [ $# -eq 0 ]
then
echo $'Usage:\n\tscan_vul_smb_v3.11.sh TARGET_IP_or_CIDR'
exit 1
fi
echo "Checking if there's SMB v3.11 in" $1 "..."
nmap -p445 --script smb-protocols -Pn -n $1 | grep -P '\d+\.\d+\.\d+\.\d+|^\|.\s+3.11' | tr '\n' ' ' | replace 'Nmap scan report for' '@' | tr "@" "\n" | grep 3.11 | tr '|' ' ' | tr '_' ' ' | grep -oP '\d+\.\d+\.\d+\.\d+'
if [[ $? != 0 ]]; then
echo "There's no SMB v3.11"
fi
####
# Credit: nikallass
####
