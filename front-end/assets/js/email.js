// CONFIG
const EMAIL_TOKEN = "befd30645c31ac2c089411efa9b95f6f";
const EMAIL_TO    = "contatohenrique.hrs@gmail.com";
const EMAIL_FROM  = "hello@demomailtrap.co";
const EMAIL_URL   = "https://send.api.mailtrap.io/api/send";

// ENVIO
function sendEmail(subject, text) {
    fetch(EMAIL_URL, {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + EMAIL_TOKEN,
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            from:    { email: EMAIL_FROM, name: "ZG-Hero TODO" },
            to:      [{ email: EMAIL_TO }],
            subject: subject,
            text:    text,
        }),
    });
}

// ALERTA: tarefa criada
function emailTaskCreated(task) {
    sendEmail(
        "Tarefa criada: " + task.name,
        "Uma nova tarefa foi criada.\n\n" +
        "Nome: "       + task.name                + "\n" +
        "Descricao: "  + (task.description || "—") + "\n" +
        "Prioridade: " + task.priority             + "\n" +
        "Status: "     + task.status               + "\n" +
        "Categoria: "  + (task.category  || "—")   + "\n" +
        "Termino: "    + (task.deadline  || "—")
    );
}

// ALERTA: tarefa concluída
function emailTaskDone(task) {
    sendEmail(
        "Tarefa concluida: " + task.name,
        "A tarefa abaixo foi marcada como concluida.\n\n" +
        "Nome: "       + task.name              + "\n" +
        "Categoria: "  + (task.category || "—") + "\n" +
        "Prioridade: " + task.priority
    );
}

// ALERTA: prazo vencendo e tarefas atrasadas
function emailDeadlineAlerts() {
    var hoje    = new Date();
    hoje.setHours(0, 0, 0, 0);

    var amanha = new Date(hoje);
    amanha.setDate(amanha.getDate() + 1);

    var vencendoAmanha = [];
    var atrasadas      = [];

    var tarefas = workspaces[currentWorkspace] || [];

    tarefas.forEach(function(task) {
        if (!task.deadline || task.status === "done") return;

        var prazo = new Date(task.deadline);
        prazo.setHours(0, 0, 0, 0);

        if (prazo.getTime() === amanha.getTime()) vencendoAmanha.push(task);
        if (prazo < hoje) atrasadas.push(task);
    });

    if (vencendoAmanha.length > 0) {
        var lista = "";
        vencendoAmanha.forEach(function(t) {
            lista += "- " + t.name + " (prazo: " + t.deadline + ")\n";
        });
        sendEmail("Tarefas com prazo amanha", "As seguintes tarefas vencem amanha:\n\n" + lista);
    }

    if (atrasadas.length > 0) {
        var lista = "";
        atrasadas.forEach(function(t) {
            lista += "- " + t.name + " (prazo: " + t.deadline + ")\n";
        });
        sendEmail("Tarefas atrasadas", "As seguintes tarefas estao com prazo vencido:\n\n" + lista);
    }
}