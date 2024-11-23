// Функция, которая выполняется, когда документ полностью загружен
$(document).ready(function() {
    $('.save-employee').on('click', saveEmployee);
    $('.deleted-employee').on('click', deleteEmployee);


    $('.add-employee').on('click', showAddEmployeeModal);
    $('.close-modal').on('click', hideAddEmployeeModal);

    $('#userList').on('change', updateUserSelectionInModal);
    $('#employeeRoleList').on('change', updateEmployeeRoleSelectionInModal);
    $('#privilegeRoleList').on('change', updatePrivilegeRoleSelectionInModal);


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
                //updateUsersList();
                $('#addEmployeeModal').modal('hide');
            },
            error: function(error) {
                console.log(`Ошибка сохранения сотрудника \n Erro: ${error.message}`);
            }
        });
    }

    function editEmployee(userId) {
        $.ajax({
            url: '/edit-employee',
            method: 'GET',
            data: { userId: userId },
            success: function(response) {
                $('#editForm').modal('show');
                $('#userId').val(response.userId);
                $('#userName').val(response.userName);
                $('#userPosition').val(response.userPosition);
                // Заполнение остальных полей...
            },
            error: function(xhr, status, error) {
                console.log('Ошибка при редактировании сотрудника');
            }
        });
    }

    function deleteEmployee(e) {
        const userId = e.currentTarget.dataset.userId ? parseInt(e.currentTarget.dataset.userId): null;
        const userName = e.currentTarget.dataset.userName ? e.currentTarget.dataset.userName: null;
        if (userId != null && confirm("Вы уверены, что хотите удалить сотрудника  " + userName + "?")) {
            $.ajax({
                url: '/employee/deleted',
                method: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(userId ),
                success: function(data) {
                    if (data) {
                        alert('Сотрудник успешно удален');
                        // Обновите список сотрудников после удаления
                        //refreshEmployeeList();
                    } else {
                        alert('Ошибка при удалении сотрудника');
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
        $('#addEmployeeModal').modal('show');
    }

    function hideAddEmployeeModal () {
        $("#employeeRoleList").attr('data-role-id', "").val('').removeClass('is-invalid');
        $("#userList").attr('data-user-id', "").val('').removeClass('is-invalid');
        $('#privilegeRoleList').attr('data-privilege', "").val('').removeClass('is-invalid')

        $('#addEmployeeModal').modal('hide');
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
});