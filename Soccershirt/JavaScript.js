//VARIAVEIS****************************************************************
//variaveis onde vamos guardar qual das opções
//de gerenciamento será feita, para facilitar
let acao = "";
let categoria = "";
//BUSCA DE DADOS ESPECIFICOS************************************************
async function buscaJsonEx(id) {
    var ans = null
    var request = await $.ajax({
        url: "Selecionar" + categoria.charAt(0).toUpperCase() + categoria.slice(1),
        type: 'POST',
        data: id,
        async: true,
        success: function (json) {
            console.log(JSON.parse(json));
            ans = JSON.parse(json);;
        }
    });
    return ans;
}
//BUSCA DE DADOS GERAIS*****************************************************
async function buscaJson(categ) {
    var ans = null
    var request = await $.ajax({
        url: "Selecionar" + categ.charAt(0).toUpperCase() + categ.slice(1) + "s",
        type: 'POST',
        async: true,
        success: function (json) {
            console.log(JSON.parse(json));
            ans = JSON.parse(json);
        }
    });
    return ans;
}
//VERIFICA ADMINISTRADOR******************************************************
function verificaAdm() {
    if ($("#txtUsername").val() == "") {
        $("#txtUsername").focus();
    } else if ($("#txtSenha").val() == "") {
        $("#txtSenha").focus();
    } else {
        //enviamos por ajax o json com os dados para a página que irá verificar os dados
        /*  var request = $.ajax({
               url: "link",
               type: 'POST',
               data: { "username": $("#txtUsername").val(), "senha": $("#txtSenha").val() },
               async: true,
               success: function (mensagem) {
                   $("section").text(`<div class="exibe" id="menu">
                    <h4>Escolha uma categoria</h4>
                    <select id="opcSelect" class="form-control">
                        <option value="notícia">Notícia</option>
                        <option value="time">Time</option>
                        <option value="marca">Marca</option>
                        <option value="liga">Liga</option>
                    </select>
                    <button id="cadastrar" class="btn btn-outline-success btn_categorias"
                        value="cadastrar">Cadastrar</button>
                    <button id="editar" class="btn btn-outline-warning btn_categorias" value="editar">Editar</button>
                    <button id="excluir" class="btn btn-outline-danger btn_categorias" value="excluir">Excluir</button>
                </div>
                <div class="exibe" id="corpo" style="display: none"></div>`);
               }
               //colocar mensagem de erro
           })
            request.done(function (msg) {
                $("#log").html(msg);
            });
            request.fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });*/
    }
}
//EDITAR************************************************************************
//chamada da função para editar
async function editar() {
    let id = $("#opcSelectG option:selected").val();
    let json = await buscaJsonEx(id);
    console.log(json)
    if (categoria == "noticia") {
        camposNoticia("Editando notícia", id);
        $("#tituloNoticia").val(json.titulo);
        $("#subtituloNoticia").val(json.subtitulo);
        $("#conteudoNoticia").val(json.texto);
        //falta os selects
    } else {
        //verificamos qual é a categoria para enviar os parametros corretos
        if (categoria == "time") {
            camposGerais("Editando time", "do", "Escudo", id);
        } else if (categoria == "marca") {
            camposGerais("Editando marca", "da", "Logo", id);
        } else {
            camposGerais("Editando liga", "da", "Símbolo", id);
            $("#paisLiga").val(json.paisOrigem);
        }
        $("#nome").val(json.nome);
        $("#customFile").val(json.img);
    }
};
//CADASTRAR********************************************************************
//chamada da função para cadastrar
function cadastrar() {
    //vamos direcionar para a função de cadastramento
    //de cada categoria
    if (categoria == "notícia") {
        camposNoticia("Cadastrando uma nova notícia", 0);
    } else if (categoria == "time") {
        camposGerais("Cadastrando um novo time", "do", "Escudo", 0);
    } else if (categoria == "marca") {
        camposGerais("Cadastrando uma nova marca", "da", "Logo", 0);
    } else {
        camposGerais("Cadastrando uma nova liga", "da", "Símbolo", 0);
    }
}
//CAMPOS DE NOTÍCIA******************************************************************
//a função 'camposNoticia()' será chamada no cadastro de notícia ou na edição dela
//terá todos os campos para o usuário inserir sobre a notícia
async function camposNoticia(textH4, textOnclick) {
    $("#corpo").text(``)
    $("#corpo").show()
    $("#corpo").append(`
            <div id="formCorpo">
                    <h4>${textH4}</h4>
                    <div class="form-group">
                        <label class="form-label">Título</label>
                        <label style = "color: red;">*</label>
                        <input type="text" id="tituloNoticia" class="form-control"
                            placeholder="Insira o título da notícia">
                    </div>
                    <div class="form-group">
                        <label class="form-label">Subtítulo</label>
                        <label style = "color: red;">*</label>
                        <textarea id="subtituloNoticia" class="form-control"
                            placeholder="Insira o subtítulo da notícia"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label">Corpo</label>
                        <label style = "color: red;">*</label>
                        <textarea style="height: 150px;" id="conteudoNoticia" class="form-control"
                            placeholder="Insira o conteúdo da notícia" aria-label="With textarea"></textarea>
                    </div>
                    <div class="form-group">
                        <label class="form-label select-label">Times - Ainda em construção</label>
                        <select id="slcTimes" class="form-control" multiple="multiple" name="ligas[]">
                            <option value="0" disabled>Selecionar time(s)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label select-label">Marcas - Ainda em construção</label>
                        <select id="slcMarcas" class="form-control" multiple="multiple" name="ligas[]">
                            <option value="0" disabled>Selecionar marca(s)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-label select-label">Ligas - Ainda em construção</label>
                        <select id="slcLigas" class="form-control" multiple="multiple" name="ligas[]">
                            <option value="0" disabled>Selecionar liga(s)</option>
                        </select>
                    </div>
                    <label class="form-label">Imagem - Ainda em construção</label>
                    <div class="form-group custom-file">
                        <input type="file" class="custom-file-input" id="customFile"
                            accept="image/png, image/gif, image/jpeg" multiple name="arquivo">
                        <label class="custom-file-label" for="customFile" style="float: left !important;">Insira imagem</label>
                    </div>
                    <br>
                    <div class="form-group" id="imgSelecionadas" style="display: none" ></div>
                    <br>
                    <div class="form-group">
                        <button type="submit" id="finalizaCadastro" style="margin-top: 20px;" onclick="enviaNoticia('${textOnclick}')" class="btn btn-outline-light form-control">Finalizar</button>
                    </div>
                </div>`)
    //buscando todos os times
    let json = await buscaJson("time");
    for (let i = 0; i < json.length; i++) {
        $("#slcTimes").append(`
                    <option value="${json[i].id}">${json[i].id} - ${json[i].nome}</option>`);
    }
    //buscando todas as marcas
    json = await buscaJson("marca");
    for (let i = 0; i < json.length; i++) {
        $("#slcMarcas").append(`
                    <option value="${json[i].id}">${json[i].id} - ${json[i].nome}</option>`);
    }
    //buscando todas as ligas
    json = await buscaJson("liga");
    for (let i = 0; i < json.length; i++) {
        $("#slcLigas").append(`
                    <option value="${json[i].id}">${json[i].id} - ${json[i].nome}</option>`);
    }
}
//ENVIA NOTÍCIA********************************************************************
//a função 'enviaNoticia()' vai ser chamada quando for
//finalizar um cadastro de notícia ou finalizar edição de notícia
function enviaNoticia(id) {
    //verifica os campos obrigatórios
    if ($("#tituloNoticia").val() == "") {
        $("#tituloNoticia").focus();
    } else if ($("#subtituloNoticia").val() == "") {
        $("#subtituloNoticia").focus();
    } else if ($("#conteudoNoticia").val() == "") {
        $("#conteudoNoticia").focus();
    } else {
        //vamos pegar os valores selecionados nos multiples selects
        //se não houver seleção a função irá retornar 0
        let times = getSelectValues(slcTimes);
        let ligas = getSelectValues(slcLigas);
        let marcas = getSelectValues(slcMarcas);

        let noticiaCompleta = "";
        let msg = "";
        //vamos passar o id para usar quando for editar
        //uma notícia, portanto verificamos qual é a ação
        noticiaCompleta = { "id": id, "titulo": $("#tituloNoticia").val(), "subtitulo": $("#subtituloNoticia").val(), "conteudo": $("#conteudoNoticia").val(), "times": times, "marcas": marcas, "ligas": ligas }
        if (id == 0) {
            msg = "Notícia cadastrada com sucesso!!!";
        } else {
            msg = "Notícia editada com sucesso!!!";
        }
        var request = $.ajax({
            url: "CadastrarNoticia",
            type: 'POST',
            contentType: "application/json",
            data: noticiaCompleta,
            async: true,
            success: function (response) {
                alert(msg);
                //limpamos os campos
                $("#formCorpo input, textarea, #customFile").val("");
            }
        })
        request.done(function (msg) {
            $("#log").html(msg);
        });
        request.fail(function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        });
        console.log(request);
    }
}
//CAMPOS TIME/MARCA/LIGA****************************************************************
function camposGerais(texth4, textCon, txtImg, textOnclick) {
    $("#corpo").text(``)
    $("#corpo").show()
    $("#corpo").append(`
            <div id="formCorpo">
                    <h4>${texth4}</h4>
                    <div class="form-group">
                        <label class="form-label">Nome</label>
                        <label style = "color: red;">*</label>
                        <input type="text" id="nome" class="form-control"
                            placeholder="Insira o nome ${textCon} ${categoria}">
                    </div>
                </div>`);
    // se for liga terá uns campos a mais
    if (categoria == "liga") {
        textCon = "do";
        $("#formCorpo").append(` <div class="form-group">
                        <label class="form-label">País de origem</label>
                        <label style = "color: red;">*</label>
                        <input id="paisLiga" class="form-control"
                            placeholder="Insira o país de origem da liga">
                    </div>`)
    }
    $("#formCorpo").append(`
                    <label class="form-label ">${txtImg}</label>
                    <label style = "color: red;">* - Em construção</label>
                    <div class="form-group custom-file">
                        <input type="file" class="custom-file-input" id="customFile"
                            accept="image/png, image/gif, image/jpeg" name="arquivo">
                        <label class="custom-file-label" for="customFile"">Insira uma foto ${textCon} ${txtImg.toLowerCase()}</label>
                    </div>
                    <br>
                    <div class="form-group" id="imgSelecionadas" style="display: none"></div>
                    <br>
                    <div class="form-group">
                        <button type="submit" id="finalizaCadastro" style="margin-top: 20px;" onclick="enviaGeral(${textOnclick})" class="btn btn-outline-light form-control">Finalizar</button>
                    </div>
                `)
}
//ENVIA TIME/MARCA/LIGA****************************************************************
function enviaGeral(id) {
    //verifica os campos obrigatórios
    if ($("#nome").val() == "") {
        $("#nome").focus();
    }/*else if ($("#customFile").val() == "") {
            $("#customFile").focus();
    }*/ else {
        //msg é apenas para manter a concordância do genêro
        let msg;
        if (id == 0) {
            msg = "cadastrada com sucesso!!!";
        } else {
            msg = "editada com sucesso!!!";
        }

        let geralCompleto = { "id": id, "nome": $("#nome").val(), "img": $("#customFile").val() };

        //se for liga, como liga tem mais campos, vamos verificar
        if (categoria == "liga") {
            if ($("#paisLiga").val() == "") {
                $("#paisLiga").focus();
            } else {
                geralCompleto = { "id": id, "nome": $("#nome").val(), "paisOrigem": $("#paisLiga").val(), "img": $("#customFile").val() };
            }
        }

        var request = $.ajax({
            url: "Cadastrar" + categoria.charAt(0).toUpperCase() + categoria.slice(1),
            type: 'POST',
            data: geralCompleto,
            async: true,
            success: function (mensagem) {
                if (categoria == "time") {
                    if (msg.charAt(1) == "c") {
                        msg = " cadastrado com sucesso!!!";
                    } else {
                        msg = "editado com sucesso!!!";
                    }
                }
                alert(categoria.charAt(0).toUpperCase() + categoria.slice(1) + msg)
                $("#formCorpo input, textarea, #customFile").val("");
            }
        })
        request.done(function (msg) {
            $("#log").html(msg);
        });
        request.fail(function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
        });
        console.log(request);
    }
}
//FUNÇÃO DE DIRECIONA PARA A AÇÃO ESCOLHIDA****************************************************************
//cliques nos botões do menu
async function funcao(param) {
    acao = param;
    //guardamos a categoria selecionada
    categoria = $("#opcSelect option:selected").val();
    //caso a ação selecionada não seja uma cadastrar
    //vamos exibir um select para o administrador selecionar
    //qual das cadastradas vão ser editada ou excluida
    if (acao != "cadastrar") {
        //aqui vamos acessar o futuro json para pegar
        //os dados cadastrados e exibir no select
        //limpa a div onde vamos exibir
        $("#corpo").text(``);
        //mostra ela
        $("#corpo").show();
        $("#corpo").append(`<div id="formCorpo">
                    <h4>Qual ${categoria} deseja ${acao}?</h4>
                    <br>
                    <select id="opcSelectG" class="form-control" required></select>
                    <br>
                    <button id="prosseguir" type="submit" class="btn btn-outline-light" onclick="${acao}('${categoria}')" >Prosseguir</button></div>
                    </form>`);
        if (categoria == "notícia") {
            categoria = "noticia";
        }
        let json = await buscaJson(categoria);
        if (json != null) {
            if (categoria == "noticia") {
                for (let i = 0; i < json.length; i++) {
                    $("#formCorpo select").append(`
                        <option value="${json[i].id}">${json[i].id} - ${json[i].titulo}</option>`);
                }
            } else {
                for (let i = 0; i < json.length; i++) {
                    $("#formCorpo select").append(`
                        <option value="${json[i].id}">${json[i].id} - ${json[i].nome}</option>`);
                }
            }
        } else {
            $("#formCorpo select").append(`<option value="0">Não há ${categoria}s cadastrados</option>`);
        }
    } else {
        cadastrar();
    }
}
//pega os valores selecionados dos multiples selects******************************************************************
function getSelectValues(select) {
    var result = [];
    var options = select && select.options;
    var b = false;

    for (var i = 0; i < options.length; i++) {
        if (options[i].selected) {
            b = true;
            result.push(parseInt(options[i].value));
        }
    }

    if (b == false) {
        result = 0;
    }
    return result;
}
//quando o adm adicionar algumas foto .change() irá chamar a função readURL()**********************************
/*$("#customFile").change(function () {
    readURL(this);
});*/
//*************************************************************************************************
function excluir(param) {
    console.log("Excluir" + param.charAt(0).toUpperCase() + param.slice(1))
    var request = $.ajax({
        url: "Excluir" + param.charAt(0).toUpperCase() + param.slice(1),
        type: 'POST',
        data: $("#opcSelectG option:selected").val(),
        async: true,
        success: function (mensagem) {
            alert(param.charAt(0).toUpperCase() + param.slice(1) + " excluído com sucesso!!!")
        }
    });
    request.done(function (msg) {
        $("#log").html(msg);
    });
    request.fail(function (jqXHR, textStatus) {
        alert("Request failed: " + textStatus);
    });
    console.log(request);
}