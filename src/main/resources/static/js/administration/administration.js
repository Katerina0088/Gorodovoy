$(document).ready(function() {
    $('.edit-employee').on('click', function() {
        editEmployee($(this).closest('tr').find('.id').text());
    });

    $('.deleted-employee').on('click', function() {
        deleteEmployee($(this).closest('tr').find('.id').text());
    });

    $('.add-employee').on('click', function() {
        modalEmploee($(this).closest('tr').find('.id').text());
    });

    $('.close-modal').on('click', function() {
        closeModal();
    });

    function addEmployee() {
        $.ajax({
            url: '/add-employee',
            method: 'POST',
            data: $('#employeeForm').serialize(),
            success: function(response) {
                updateUsersList();
                $('#editForm').modal('hide');
            },
            error: function(xhr, status, error) {
                console.log('Ошибка при добавлении сотрудника');
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

    function modalEmploee() {
        $('#addEmployeeModal').modal('show');
    }

    function closeModal() {
        $('#addEmployeeModal').modal('hide');
    }
});
