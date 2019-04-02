echo off
cls
echo criando arquivo de configuracao

del C:\krtv\configuracao.properties
rmdir C:\krtv
mkdir C:\krtv

set /p id_cliente=Informe o codigo do cliente? 

echo config.remover_arquivos = true > C:\krtv\configuracao.properties
echo config.taxa_atualizacao = 1 >> C:\krtv\configuracao.properties
echo config.path_arquivos = C:\Users\%id_cliente%\AppData\Roaming\SignagePlayer.86EE3EEE54D7DB049D16E358CDC443F088917621.1\Local Store\signage.me\business%id_cliente%\Resources >> C:\krtv\configuracao.properties
echo config.url_lista = http://www.krtv.info/lista/%id_cliente%.txt >> C:\krtv\configuracao.properties
echo config.id_cliente = %id_cliente% >> C:\krtv\configuracao.properties


pause