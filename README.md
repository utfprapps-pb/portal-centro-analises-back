![image](https://user-images.githubusercontent.com/80293325/223904256-9c578d48-4230-4606-bcd4-f7a8047f968f.png)



Mock EndPoints ==>

http://localhost:8085/api/v1/equipments/
Equipamentos {
"name" : "maquina1",
"value_hour_utfpr" : 10.0,
"value_hour_partner" : 12.0,
"value_hour_pf_pj" : 15.0,
"value_sample_utfpr" : 20,
"value_sample_partner" : 25,
"value_sample_pf_pj" : 30
}


AQUI REGISTRA
http://localhost:8085/api/v1/users

Usuarios {
"name" : "Marcelo",
"username" : "marcelofalchi",
"password" :  "falchizao",
"email" : "marcelonavarro@gmail.com"
}

AQUI FAZ O LOGIN
http://localhost:8085/api/login
{
"username" : "marcelofalchi",
"password" :  "falchizao",
}