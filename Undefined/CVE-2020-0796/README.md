# CVE-2020-0796
<h2>CVE-2020-0796 - a wormable SMBv3 vulnerability.</h2>

<h3>1. Information.</h3>
<h3>2. Scan Vul.</h3>
<h3>3. Infected system.</h3>
<h3>4. Demo POC.</h3>
<h3>5. Exploit Script:</h3>
<h3>6. How to prevent.</h3>
<h3>7. How to detect?.</h3>
<h3>8. FAQ.</h3>

<hr>

<h2>1. Information: </h2>
<pre>
Microsoft is aware of a remote code execution vulnerability in the way that the Microsoft Server Message Block 3.1.1 (SMBv3) protocol handles certain requests. An attacker who successfully exploited the vulnerability could gain the ability to execute code on the target SMB Server or SMB Client.

To exploit the vulnerability against an SMB Server, an unauthenticated attacker could send a specially crafted packet to a targeted SMBv3 Server. To exploit the vulnerability against an SMB Client, an unauthenticated attacker would need to configure a malicious SMBv3 Server and convince a user to connect to it.
</pre>

<h2>2. Scan Vul:</h2>
<pre>
  + Usage <a href='https://github.com/cve-2020-0796/cve-2020-0796/blob/master/CVE-2020-0796_Scan_Vul.py'>CVE-2020-0796_Scan_Vul.py</a>: python3 CVE-2020-0796_Scan_Vul.py [IP]
  + Usage <a href='https://github.com/cve-2020-0796/cve-2020-0796/blob/master/scan_vul_smb_v3.11.sh'>scan_vul_smb_v3.11.sh</a>: ./scan_vul_smb_v3.11.sh [IP]
</pre> 
<h2>3. Infected system:</h2>
<pre>
  + Windows 10 Version 1903 for 32-bit Systems
  + Windows 10 Version 1903 for ARM64-based Systems
  + Windows 10 Version 1903 for x64-based Systems
  + Windows 10 Version 1909 for 32-bit Systems
  + Windows 10 Version 1909 for ARM64-based Systems
  + Windows 10 Version 1909 for x64-based Systems
  + Windows Server, version 1903 (Server Core installation)
  + Windows Server, version 1909 (Server Core installation)
 </pre> 
<h2>4. Demo POC:</h2>
<a href="https://vimeo.com/397149983" target="_blank">Watch Video POC</a>

<h2>5. Exploit Script:</h2>
<pre>Public on 18 March 2020 (After 5 days release patch update from Vendor).</pre>

<h2>6. How to prevent?</h2>
  <b>Patch update from Microsoft is available, update now.</b>
  <a href="http://www.catalog.update.microsoft.com/Search.aspx?q=KB4551762">Download Patch Now - KB4551762</a>

  <br><br>Workacounds: Disable SMBv3 compression: You can disable compression to block unauthenticated attackers from exploiting the vulnerability against an SMBv3 Server with the   PowerShell command below.
<pre>
Set-ItemProperty -Path "HKLM:\SYSTEM\CurrentControlSet\Services\LanmanServer\Parameters" DisableCompression -Type DWORD -Value 1 -Force
</pre>

<h2>7. How to detect?.</h2>
Rule Snort:
<pre>
###############
# Rules by Claroty
# This rules will detect SMB compressed communication by the SMB protocol identifier. 
# The use of the offset and depth parameter is designed to prevent false positives and to allow the NetBios Layer
###############
alert tcp any any -> any 445 (msg:"Claroty Signature: SMBv3 Used with compression - Client to server"; content:"|fc 53 4d 42|"; offset: 0; depth: 10; sid:1000001; rev:1; reference:url,//blog.claroty.com/advisory-new-wormable-vulnerability-in-microsoft-smbv3;)
alert tcp any 445 -> any any (msg:"Claroty Signature: SMBv3 Used with compression - Server to client"; content:"|fc 53 4d 42|"; offset: 0; depth: 10; sid:1000002; rev:1; reference:url,//blog.claroty.com/advisory-new-wormable-vulnerability-in-microsoft-smbv3;)
</pre>

<h2>8. FAQ</h2>

  + Block TCP port 445 at the enterprise perimeter firewall
<pre>TCP port 445 is used to initiate a connection with the affected component. Blocking this port at the network perimeter firewall will help protect systems that are behind that firewall from attempts to exploit this vulnerability. This can help protect networks from attacks that originate outside the enterprise perimeter. Blocking the affected ports at the enterprise perimeter is the best defense to help avoid Internet-based attacks. However, systems could still be vulnerable to attacks from within their enterprise perimeter.</pre>
  + Follow Microsoft guidelines to prevent SMB traffic from lateral connections and entering or leaving the network
    <a href='https://support.microsoft.com/en-us/help/3185535/preventing-smb-traffic-from-lateral-connections'>Preventing SMB traffic from lateral connections and entering or leaving the network</a>
  + Are older versions of Windows (other than what is listed in the Security Updates table) affected by this vulnerability?
<pre>No, the vulnerability exists in a new feature that was added to Windows 10 version 1903. Older versions of Windows do not support SMBv3.1.1 compression.</pre>
