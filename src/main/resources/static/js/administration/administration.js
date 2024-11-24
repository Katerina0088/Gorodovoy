// Функция, которая выполняется, когда документ полностью загружен
$(document).ready(function() {
    $('.save-employee').on('click', saveEmployee);
    $('.deleted-employee').on('click', deleteEmployee);

    $('.add-employee').on('click', showAddEmployeeModal);
    $('.close-modal').on('click', hideAddEmployeeModal);
    $('.edit-employee').on('click', showEditEmployeeModal);

    $('#userList').on('change', updateUserSelectionInModal);
    $('#employeeRoleList').on('change', updateEmployeeRoleSelectionInModal);
    $('#privilegeRoleList').on('change', updatePrivilegeRoleSelectionInModal);

    $('#successAlert').on('click', hideSuccessAlert);
    $('#successAlert .btn-close').on('click', hideSuccessAlert);
    $('#dangerAlert').on('click', hideDangerAlert);
    $('#dangerAlert .btn-close').on('click', hideDangerAlert);

    $(document).ajaxSend(function(e, xhr, settings) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });

    function saveEmployee(e) {
        const userId = $("#userList")[0].dataset.userId,
            roleId = $("#employeeRoleList")[0].dataset.roleId,
            privilegeRole = $("#privilegeRoleList")[0].dataset.privilege,
            employee = {
                userId: userId ? parseInt(userId) : null,
                roleId: roleId ? parseInt(roleId) : null,
                privilegeRole: privilegeRole ? privilegeRole: null
            };

        if(userId===''){
            $("#userList").addClass('is-invalid');
            return;
        }
        if(roleId===''){
            $("#employeeRoleList").addClass('is-invalid');
            return;
        }

        $.ajax({
            url: '/employee/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify( {employee:employee, privilegeRole:privilegeRole }),
            success: function(response) {
                reloadEmployees();
                hideAddEmployeeModal()
                showSuccessAlert();
            },
            error: function(error) {
                showDangerAlert("Ошибка сохранения сотрудника");
                console.log(`Ошибка сохранения сотрудника \n Error: ${error.message}`);
            }
        });
    }
    function deleteEmployee(e) {
        const userId = e.currentTarget.dataset.userId ? parseInt(e.currentTarget.dataset.userId): null;
        const userName = e.currentTarget.dataset.userName ? e.currentTarget.dataset.userName: null;
        const classEmployeeId = e.currentTarget.parentElement.parentElement.className;
        if (userId != null && confirm("Вы уверены, что хотите удалить сотрудника  " + userName + "?")) {
            $.ajax({
                url: '/employee/deleted',
                method: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(userId ),
                success: function(data) {
                    if (data) {
                        //alert('Сотрудник успешно удален');
                        $(`.${classEmployeeId}`).remove()
                        showSuccessAlert();
                    } else {
                        showDangerAlert("Ошибка при удалении сотрудника");
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Ошибка при удалении сотрудника:', error);
                    alert('Произошла ошибка при удалении сотрудника');
                }
            });
        }
    }

    // Work with Modal
    function showAddEmployeeModal() {
        $('#employeeModal').modal('show');
    }

    function showEditEmployeeModal(e){

        const employeeString = e.currentTarget.dataset.employee;
        if (!employeeString) {
            console.error('Атрибут data-employee не найден');
            showDangerAlert("Атрибут data-employee не найден")
            return null;
        }

        const parts = employeeString.split('-');

        const employeeId = parts[0]?  parseInt(parts[0], 10): null;
        const userId = parts[1]? parts[1]: nul;
        const roleId = parseInt(parts[2], 10)? parseInt(parts[2], 10): nul;
        const privilegeRole = parts[3]? parts[3]: null;

        const form = $('#employeeForm');

        // Заполняем поля формы
        form.find('#userList').val(userId);
        form.find('#employeeRoleList').val(roleId);
        form.find('#privilegeRoleList').val(privilegeRole);


        // Заполняем поле пользователя
        const userName = form.find('#dataUser option[data-user-id="' + userId + '"]').val();
        form.find('#userList').val(userName);
        form.find('#userList').attr('data-user-id', userId);


        // Заполняем поле роли сотрудника
        const roleName = form.find('#dataRole option[data-role-id="' + roleId + '"]').val();
        form.find('#employeeRoleList').val(roleName);
        form.find('#employeeRoleList').attr('data-role-id', roleId);


        // Заполняем поле привилегий
        const privilegeName = form.find('#dataPrivilege option[data-privilege="' + privilegeRole + '"]').val();
        form.find('#privilegeRoleList').val(privilegeName);
        form.find('#privilegeRoleList').attr('data-privilege', privilegeRole);


        $('#employeeModal').modal('show');
    }

    function hideAddEmployeeModal () {
        $("#employeeRoleList").attr('data-role-id', "").val('').removeClass('is-invalid');
        $("#userList").attr('data-user-id', "").val('').removeClass('is-invalid');
        $('#privilegeRoleList').attr('data-privilege', "").val('').removeClass('is-invalid')

        $('#employeeModal').modal('hide');
    }

    function updateUserSelectionInModal (e) {
        const selectedOption = e.target.value;

        if (selectedOption) {
            const option = $('#dataUser').find(`[value="${selectedOption}"]`);
            if (option) {
                const userId = option.attr('data-user-id');
                if (userId) {
                    $("#userList").attr('data-user-id', userId);
                    console.log('ID выбранного пользователя:', userId);
                } else
                    console.warn('ID пользователя не найдено в опции');
            } else
                console.log('Пользователь не выбран');

        } else
            console.log('Пользователь не выбран');

    }

    function updateEmployeeRoleSelectionInModal (e) {
        const selectedOption = e.target.value;

        if (selectedOption) {
            const option = $('#dataRole').find(`[value="${selectedOption}"]`);
            if (option) {
                const roleId = option.attr('data-role-id');
                if (roleId) {
                    $("#employeeRoleList").attr('data-role-id', roleId);
                    console.log('ID выбранной роли:', roleId);
                } else
                    console.warn('ID роли не найдено в опции');
            } else
                console.log('Роль не выбран');

        } else
            console.log('Роль не выбран');

    }

    function updatePrivilegeRoleSelectionInModal (e) {
        const selectedOption = e.target.value;

        if (selectedOption) {
            const option = $('#dataPrivilege').find(`[value="${selectedOption}"]`);
            if (option) {
                const privilegeRole = option.attr('data-privilege');
                if (privilegeRole) {
                    $("#privilegeRoleList").attr('data-privilege', privilegeRole);
                    console.log('privilegeRole выбранного пользователя:', privilegeRole);
                } else
                    console.warn('privilegeRole пользователя не найдено в опции');
            } else
                console.log('privilegeRole не выбран');

        } else
            console.log('privilegeRole не выбран');

    }

    // Функция для обновления списка сотрудников
    function reloadEmployees() {
        $.ajax({
            url: '/employee/list',
            method: 'GET',
            success: function(response) {
                // Обновляем контент таблицы
                $('.table-striped tbody').empty();
                $.each(response, function(index, employee) {
                    var row = $('<tr>').addClass(`employee-id-${employee.id}`)
                        .append($('<td>').text(employee.user.id))
                        .append($('<td>').text(employee.user.firstName))
                        .append($('<td>').text(employee.user.lastName))
                        .append($('<td>').text(employee.user.birthDate))
                        .append($('<td>').text(employee.role.roleName))
                        .append($('<td>').text(employee.user.role))
                        .append($('<td>')
                            .append($('<button>').addClass('btn btn-primary edit-employee')
                                .attr('data-employee', employee.id + '-' + employee.user.id + '-' + employee.role.id + '-' + employee.user.role)
                                .text('Редактировать'))
                        .append($('<button>').addClass('btn btn-danger deleted-employee')
                            .attr('data-user-id', employee.user.id)
                            .attr('data-user-name', employee.user.firstName + ' ' + employee.user.lastName)
                            .text('Удалить')
                        )
                    );
                    $('.table-striped tbody').append(row);
                });
                $('.edit-employee').off().on('click', showEditEmployeeModal);
                $('.deleted-employee').off().on('click', deleteEmployee);
            },
            error: function(xhr, status, error) {
                console.error('Ошибка при получении списка сотрудников:', error);
            }
        });
    }

    // Функция для показа уведомления
    function showSuccessAlert() {
        $('#successAlert').removeClass('d-none');
        setTimeout(function() {
            $('#successAlert').addClass('d-none');
        }, 10000); // 10 секунд
    }
    function hideSuccessAlert() {
        $('#successAlert').addClass('d-none');
    }

    function showDangerAlert(message) {
        if(message)
            $('#dangerAlert .alert-message strong').text(message);
        else
            $('#dangerAlert .alert-message strong').text(`Операция завершилась с ошибкой`);
        $('#dangerAlert').removeClass('d-none');
        setTimeout(function() {
            $('#dangerAlert').addClass('d-none');
        }, 5000); // 10 секунд
    }
    function hideDangerAlert() {
        $('#dangerAlert').addClass('d-none');
    }
});