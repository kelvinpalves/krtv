; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{4B9BCE3F-8318-43C7-8932-9B55B855EC55}
AppName=KRTV
AppVersion=1.0.2
;AppVerName=KRTV 1.0.2
AppPublisher=Forge IT Solutions
AppPublisherURL=http://www.forgeit.com.br/
AppSupportURL=http://www.forgeit.com.br/
AppUpdatesURL=http://www.forgeit.com.br/
DefaultDirName={pf}\KRTV
DisableProgramGroupPage=yes
OutputBaseFilename=setup
SetupIconFile=C:\Users\user\Downloads\favicon.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "brazilianportuguese"; MessagesFile: "compiler:Languages\BrazilianPortuguese.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "C:\Users\user\Downloads\KRTV-1.0.5.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\config.bat"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\KRTV"; Filename: "{app}\KRTV-1.0.5.exe"
Name: "{commondesktop}\KRTV"; Filename: "{app}\KRTV-1.0.5.exe"; Tasks: desktopicon

[Run]
Filename: "{app}\config.bat"; Flags: postinstall

