@echo off
rem setup a shortcut to:
rem C:\Windows\System32\cmd.exe /k X:\dev\setup.bat X \dev
rem where X is the drive letter
if /%1/ == // goto usage
if /%2/ == // goto usage
set DEV_DRIVE_LETTER=%1
set DEV_PATH=%1:%2
%DEV_DRIVE_LETTER%:
cd %DEV_PATH%
set DEV_TOOLS=%DEV_PATH%\tools
call %DEV_PATH%\user.bat
set USERPROFILE=%DEV_PATH%\home\%USERNAME%
set HOME=%DEV_PATH%\home\%USERNAME%
path %DEV_TOOLS%;%path%
call %DEV_TOOLS%\setup.bat
cd %HOME%
goto end
:usage
echo usage: setup.bat D dev
echo (where D is a drive letter with colon missing,
echo and dev is the folder)
:end