echo off
cls
echo criando arquivo de configuracao

del C:\krtv\configuracao.properties
rmdir C:\krtv
mkdir C:\krtv

echo config.remover_arquivos = true > C:\krtv\configuracao.properties
echo config.taxa_atualizacao = 10 >> C:\krtv\configuracao.properties
echo config.path_arquivos = C:\\Users\\Administrator\\AppData\\Roaming\\SignagePlayer.86EE3EEE54D7DB049D16E358CDC443F088917621.1\\Local Store\\signage.me\\business%id_cliente%\\Resources >> C:\krtv\configuracao.properties
echo config.url_lista = http://www.krtv.info/lista/ >> C:\krtv\configuracao.properties
echo config.id_cliente = %id_cliente% >> C:\krtv\configuracao.properties
echo config.path_inicial = C:\\Users\\Administrator\\AppData\\Roaming\\SignagePlayer.86EE3EEE54D7DB049D16E358CDC443F088917621.1\\Local Store\\signage.me >> C:\krtv\configuracao.properties

SchTasks /create /tn "Kelvin" /sc minute /mo 5 /tr "C:\tmp\krtv-exe.exe"   

pause