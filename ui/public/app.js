const API_HOST = window._env_.API_HOST || "http://localhost:8080";
let currentUser = null;

// Загружаем данные пользователя
async function loadUser() {
    console.log("Loading user...");
    const resp = await fetch(
        `${API_HOST}/api/v1/auth/me`,
        {credentials: "include"}
    );
    if (!resp.ok) {
        alert("Ошибка загрузки пользователя");
        return;
    }
    currentUser = await resp.json();
    renderAllForms();
}

// === Формы ===

function renderPasswordForm() {
    const el = document.getElementById("password_form");
    el.innerHTML = `
      <form id="passwordChangeForm">
        <table style="width:100%;padding:10px;background-color:whitesmoke;">
          <tr>
            <td style="font-weight:bold;">Логин</td>
            <td colspan="2">${currentUser.login}</td>
          </tr>
          <tr>
            <td style="font-weight:bold;">Изменить пароль</td>
            <td>
                <p>
                    Пароль: <input name="password" type="password" required/>
                </p>
                <p>
                    Повторите пароль: <input name="confirm_password" type="password" required/>
                </p>
            </td>
            <td style="text-align:right">
                <button>Изменить пароль</button>
            </td>
          </tr>
        </table>
      </form>
    `;
    document.getElementById("passwordChangeForm").onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target));
        const resp = await fetch(`${API_HOST}/api/user/${currentUser.login}/editPassword`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });
        if (resp.ok) alert("Пароль изменён");
        else alert("Ошибка при смене пароля");
    };
}

function renderUserAccountsForm() {
    const el = document.getElementById("user_accounts_form");
    const accs = currentUser.accounts.map(a => `
        <tr>
            <td style="font-weight:bold;">${a.currency.title}</td>
            <td>${a.exists ? (a.value + " " + a.currency.name) : ""}</td>
            <td style="text-align:right">
                <input type="checkbox" name="account" value="${a.currency.name}" ${a.exists ? "checked" : ""}/>
            </td>
        </tr>`).join("");

    el.innerHTML = `
      <form id="userAccountsForm">
        <table style="width:100%;padding:10px;background-color:whitesmoke;">
          <tr>
            <td style="font-weight:bold;">Фамилия Имя</td>
            <td>${currentUser.name || ""}</td>
            <td><input name="name" type="text" style="width:100%" value="${currentUser.name || ""}"/></td>
          </tr>
          <tr>
            <td style="font-weight:bold;">Дата рождения</td>
            <td>${currentUser.birthdate || ""}</td>
            <td><input name="birthdate" type="date" style="width:100%" value="${currentUser.birthdate || ""}"/></td>
          </tr>
          ${accs}
          <tr>
            <td colspan="3" style="text-align:right"><button>Сохранить изменения</button></td>
          </tr>
        </table>
      </form>
    `;
    document.getElementById("userAccountsForm").onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target));
        const resp = await fetch(`${API_HOST}/api/user/${currentUser.login}/editUserAccounts`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });
        if (resp.ok) alert("Аккаунт обновлён");
        else alert("Ошибка при обновлении");
    };
}

function renderCashForm() {
    const el = document.getElementById("cash_form");
    const opts = currentUser.currencies.map(c => `<option value="${c.name}">${c.title}</option>`).join("");
    el.innerHTML = `
      <form id="cashForm">
        <table style="width:100%;padding:10px;background-color:whitesmoke;">
          <tr>
            <td style="font-weight:bold;">Наличные</td>
            <td>
                Валюта <select name="currency">${opts}</select>
            </td>
            <td><input name="value" type="number" required/></td>
            <td style="text-align:right">
                <button name="action" value="PUT">Положить</button>
                <button name="action" value="GET">Снять</button>
            </td>
          </tr>
        </table>
      </form>
    `;
    document.getElementById("cashForm").onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target));
        const resp = await fetch(`${API_HOST}/api/user/${currentUser.login}/cash`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });
        if (resp.ok) alert("Операция выполнена");
        else alert("Ошибка при операции");
    };
}

function renderTransferSelfForm() {
    const el = document.getElementById("transfer_self_form");
    const opts = currentUser.currencies.map(c => `<option value="${c.name}">${c.title}</option>`).join("");
    el.innerHTML = `
      <form id="transferSelfForm">
        <table style="width:100%;padding:10px;background-color:whitesmoke;">
          <tr>
            <td style="font-weight:bold;">Перевод себе</td>
            <td>Со счета <select name="from_currency">${opts}</select></td>
            <td>На счет <select name="to_currency">${opts}</select></td>
            <td><input name="value" type="number" required/></td>
            <td style="text-align:right"><button>Перевести</button></td>
          </tr>
        </table>
      </form>
    `;
    document.getElementById("transferSelfForm").onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target));
        data.to_login = currentUser.login;
        const resp = await fetch(`${API_HOST}/api/user/${currentUser.login}/transfer`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });
        if (resp.ok) alert("Перевод выполнен");
        else alert("Ошибка при переводе");
    };
}

async function renderTransferOtherForm() {
    const el = document.getElementById("transfer_other_form");
    const opts = currentUser.currencies.map(c => `<option value="${c.name}">${c.title}</option>`).join("");
    const resp = await fetch(`${API_HOST}/api/users`);
    const users = await resp.json();
    const userOpts = users.map(u => `<option value="${u.login}">${u.name}</option>`).join("");
    el.innerHTML = `
      <form id="transferOtherForm">
        <table style="width:100%;padding:10px;background-color:whitesmoke;">
          <tr>
            <td style="font-weight:bold;">Перевод другому</td>
            <td>Со счета <select name="from_currency">${opts}</select></td>
            <td>На счет <select name="to_currency">${opts}</select></td>
            <td><input name="value" type="number" required/></td>
            <td>Кому <select name="to_login">${userOpts}</select></td>
            <td style="text-align:right"><button>Перевести</button></td>
          </tr>
        </table>
      </form>
    `;
    document.getElementById("transferOtherForm").onsubmit = async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(e.target));
        const resp = await fetch(`${API_HOST}/api/user/${currentUser.login}/transfer`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(data)
        });
        if (resp.ok) alert("Перевод выполнен");
        else alert("Ошибка при переводе");
    };
}

// Курсы валют
async function loadRates() {
    const td = document.getElementById('exchange_rates');
    try {
        const response = await fetch(`${API_HOST}/api/rates`);
        const json = await response.json();
        let table = '<table style="width:100%;padding:10px;background-color:whitesmoke;">';
        table += '<tr><th colspan="3">Курсы валют по отношению к рублю</th></tr>';
        table += '<tr><th>Валюта</th><th>Обозначение</th><th>Курс</th></tr>';
        json.forEach(rate => {
            table += `<tr><td>${rate.title}</td><td>${rate.name}</td><td>${rate.value}</td></tr>`;
        });
        table += '</table>';
        td.innerHTML = table;
    } catch (e) {
        td.innerHTML = 'Ошибка при получении данных курсов валют';
    }
}

function renderAllForms() {
    renderPasswordForm();
    renderUserAccountsForm();
    renderCashForm();
    renderTransferSelfForm();
    renderTransferOtherForm();
}

// init
window.onload = () => {
    loadUser();
    setInterval(loadRates, 1000);
};
