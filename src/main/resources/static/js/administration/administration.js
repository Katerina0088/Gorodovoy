$(document).ready(function() {
    $('.save-employee').on('click', saveEmployee);
    $('.deleted-employee').on('click', () => {
        deleteEmployee($(this).closest('tr').find('.id').text());
    });

    $('.add-employee').on('click', showAddEmployeeModal);
    $('.close-modal').on('click', hideAddEmployeeModal);

    $('#userList').on('change', updateUserSelectionInModal);
    $('#employeeRoleList').on('change', updateEmployeeRoleSelectionInModal);

    $(document).ajaxSend(function(e, xhr, settings) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });

    function saveEmployee(e) {
        const userId = $("#userList")[0].dataset.userId,
            roleId = $("#employeeRoleList")[0].dataset.roleId,
            employee = {
                userId: userId ? parseInt(userId) : null,
                roleId: roleId ? parseInt(roleId) : null
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
            data: JSON.stringify(employee),
            success: function(response) {
                updateUsersList();
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

    function deleteEmployee(userId) {
        if (confirm('Вы уверены, что хотите удалить этого сотрудника?')) {
            $.ajax({
                url: '/delete-employee',
                method: 'POST',
                data: { userId: userId },
                success: function(response) {
                    updateUsersList();
                },
                error: function(xhr, status, error) {
                    console.log('Ошибка при удалении сотрудника');
                }
            });
        }
    }

    function updateUsersList() {
        $.ajax({
            url: '/get-users-in-employee',
            method: 'GET',
            success: function(response) {
                $('#usersInEmployee').empty().append(response);
            },
            error: function(xhr, status, error) {
                console.log('Ошибка при получении списка пользователей');
            }
        });
    }

    // Work with Modal
    function showAddEmployeeModal() {
        $('#addEmployeeModal').modal('show');
    }

    function hideAddEmployeeModal () {
        $("#employeeRoleList").attr('data-role-id', "").val('').removeClass('is-invalid');
        $("#userList").attr('data-user-id', "").val('').removeClass('is-invalid');

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
});