let add = document.forms["add"];

createNewUser();

function createNewUser() {
    add.addEventListener("submit", ev => {
        ev.preventDefault();

        let rolesForNewUser = [];
        for (let i = 0; i < add.roles.options.length; i++) {
            if (add.roles.options[i].selected)
                rolesForNewUser.push({
                    id: add.roles.options[i].value,
                    name: "ROLE_" + add.roles.options[i].text
                });
        }

        fetch("http://localhost:8090/api/admin/users/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: add.username.value,
                lastname: add.lastname.value,
                age: add.age.value,
                email: add.email.value,
                password: add.password.value,
                roles: rolesForNewUser
            })
        }).then(() => {
            add.reset();
            getAllUsers();
            $('#usersTable').click();

        });
    });
}

function loadRolesForNewUser() {
    let selectAdd = document.getElementById("create-roles");

    selectAdd.innerHTML = "";

    fetch("http://localhost:8090/api/admin/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name.toString().replace('ROLE_', '');
                selectAdd.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesForNewUser);