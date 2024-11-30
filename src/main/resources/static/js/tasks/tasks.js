$(document).ready(function() {

    //вызов и закрытие модального окна
    $('.add-task').on('click', showAddTaskModal);
    $('.close-modal').on('click', hideAddTaskModal);
    $('.edit-task').on('click', showEditTasksModal);
    $('.task-filter-search').on('click',getTasksFilter);
    $('.save-task').on('click', saveTask);
    $('.edit-tasks').on('click', updateTask);
    $('.deleted-task').on('click', deleteTask);
// Обработка изменения выбранного пользователя в списке
    $('#userList').on('change', updateCurrentUserSelectionInModal);
    $('#typeOfServiceList').on('change', updateTypeOfServiceListSelectionInModal);
    $('#statusList').on('change', updateStatusListSelectionInModal);
    $('#userEmployeeList').on('change', updateuserEmployeeListSelectionInModal);

// Обработка закрытия уведомлений об ошибках/успехах
    $('#successAlert').on('click', hideSuccessAlert);
    $('#successAlert .btn-close').on('click', hideSuccessAlert);
    $('#dangerAlert').on('click', hideDangerAlert);
    $('#dangerAlert .btn-close').on('click', hideDangerAlert);

    // Установка заголовка CSRF-токена для AJAX-запросов
    $(document).ajaxSend(function(e, xhr, settings) {
        const token = $("meta[name='_csrf']").attr("content");
        const header = $("meta[name='_csrf_header']").attr("content");
        xhr.setRequestHeader(header, token);
    });

    // Функция для отображения модального окна добавления задачи
    function showAddTaskModal() {
        $('#taskModal').modal('show');
        $('.modal-button-save').addClass('save-task');
        $('.save-task').off().on('click', saveTask);
    }

    // Функция для отображения модального окна редактирования задач
    function showEditTasksModal(e){

        // Извлечение деталей задачи из атрибута data которые подается в кнопке редактирования
        const tasksString = e.currentTarget.dataset.task;
        if (!tasksString) {
            console.error('Атрибут data-task не найден');
            showDangerAlert("Атрибут data-task не найден")
            return null;
        }
        const parts = tasksString.split('-');
        const taskId = parts[0]?  parseInt(parts[0], 10): null;
        const userId = parseInt(parts[1], 10)? parseInt(parts[1], 10): null;
        const serviceId = parseInt(parts[2], 10)? parseInt(parts[2], 10): null;
        const statusId = parseInt(parts[3], 10)? parseInt(parts[3], 10): null;
        const employeeId = parts[4] ? parseInt(parts[4], 10): null;
        const form = $('#taskForm');

        // Заполняем поля формы
        form.find('#userList').val(userId);
        form.find('#typeOfServiceList').val(serviceId);
        form.find('#statusList').val(statusId);
        form.find('#userEmployeeList').val(employeeId);

        // Заполняем поле пользователя
        const userName = form.find('#dataUser option[data-user-id="' + userId + '"]').val();
        form.find('#userList').val(userName);
        form.find('#userList').attr('data-user-id', userId);

        // Заполняем поле вид услуги
        const serviceName = form.find('#dataTypeOfService option[data-service-id="' + serviceId + '"]').val();
        form.find('#typeOfServiceList').val(serviceName);
        form.find('#typeOfServiceList').attr('data-service-id',serviceId);

        // Заполняем поле статус
        const statusName = form.find('#dataStatus option[data-status-id="' + statusId + '"]').val();
        form.find('#statusList').val(statusName);
        form.find('#statusList').attr('data-status-id',statusId);

        // Заполняем поле сотрудник
        const employeeName = form.find('#dataUserEmployee option[data-user-employee-id="' + statusId + '"]').val();
        form.find('#userEmployeeList').val(employeeName);
        form.find('#userEmployeeList').attr('data-user-employee-id',employeeId);

        $('#taskModal').modal('show');
        $('.modal-button-save').addClass('edit-tasks')
        $('.modal-button-save').attr('data-id', taskId)
        $('.edit-tasks').off().on('click', updateTask);

    }

    function hideAddTaskModal () {
        //$("#userList").attr('data-user-id', "").val('').removeClass('is-invalid');
        $("#typeOfServiceList").attr('data-service-id', "").val('').removeClass('is-invalid');
        $('#statusList').attr('data-status-id', "").val('').removeClass('is-invalid')
        $('#userEmployeeList').attr('data-user-employee-id', "").val('').removeClass('is-invalid')
        $('.modal-button-save').removeClass('edit-tasks')
        $('.modal-button-save').removeClass('save-task')
        $('.modal-button-save').removeAttr('data-id');

        $('#taskModal').modal('hide');
    }

    // работа с полями в модальном окне
    function updateCurrentUserSelectionInModal (e) {
        // Получаем значение выбранного элемента
        const selectedOption = e.target.value;

        if (selectedOption) {
            // Ищем опцию в списке dataUser, соответствующую выбранному значению
            //найди элемент <option> внутри <datalist> с идентификатором dataUser, у которого атрибут value соответствует значению переменной selectedOption
            const option = $('#dataUser').find(`[value="${selectedOption}"]`);
            if (option) {
                // Получаем ID пользователя из атрибута data-user-id
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

    function updateTypeOfServiceListSelectionInModal (e) {
        const selectedOption = e.target.value;

        if (selectedOption) {
            const option = $('#dataTypeOfService').find(`[value="${selectedOption}"]`);
            if (option) {
                const serviceId = option.attr('data-service-id');
                if (serviceId) {
                    $("#typeOfServiceList").attr('data-service-id', serviceId);
                    console.log('ID выбранной услуги:', serviceId);
                } else
                    console.warn('ID услуги не найдено в опции');
            } else
                console.log('Услуга не выбрана');

        } else
            console.log('Услуга не выбрана');

    }

    function updateStatusListSelectionInModal (e) {
        const selectedOption = e.target.value;

        if (selectedOption) {
            const option = $('#dataStatus').find(`[value="${selectedOption}"]`);
            if (option) {
                const StatusId = option.attr('data-status-id');
                if (StatusId) {
                    $("#statusList").attr('data-status-id', StatusId);
                    console.log('ID выбранного статуса:', StatusId);
                } else
                    console.warn('ID статуса не найдено в опции');
            } else
                console.log('Статус не выбран');

        } else
            console.log('Статус не выбран');

    }

    function updateuserEmployeeListSelectionInModal (e) {
        const selectedOption = e.target.value;

        if (selectedOption) {
            const option = $('#dataUserEmployee').find(`[value="${selectedOption}"]`);
            if (option) {
                const userEmployeeId = option.attr('data-user-employee-id');
                if (userEmployeeId) {
                    $("#userEmployeeList").attr('data-user-employee-id', userEmployeeId);
                    console.log('ID выбранного сотрудника:', userEmployeeId);
                } else
                    console.warn('ID сотрудника не найдено в опции');
            } else
                console.log('Сотрудник не выбран');

        } else
            console.log('Сотрудник не выбран');

    }


    function saveTask(e) {
        const userId = $("#userList")[0]?.dataset.userId ? $("#userList")[0].dataset.userId: null,//$(".userList")[0].dataset.userId,
            serviceId = $("#typeOfServiceList")[0].dataset.serviceId,
            statusId = $("#statusList")[0]?.dataset.statusId ? $("#statusList")[0].dataset.statusId: null,
            employeeUserId= $("#userEmployeeList")[0]?.dataset.userEmployeeId ? $("#userEmployeeList")[0].dataset.userEmployeeId : null,
            task = {
                userId: userId ? parseInt(userId) : null,
                serviceId: serviceId ? parseInt(serviceId) : null,
                statusId: statusId ? parseInt(statusId) : null,
                employeeId: employeeUserId ? parseInt(employeeUserId) : null
            };



        $.ajax({
            url: '/tasks/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(task),
            success: function(response) {
                reloadTasks();
                hideAddTaskModal()
                showSuccessAlert();
            },
            error: function(error) {
                showDangerAlert("Ошибка сохранения задачи");
                console.log(`Ошибка сохранения задачи \n Error: ${error.message}`);
            }
        });
    }

    function updateTask(e) {
        const userId = $("#userList")[0]?.dataset.userId ? $("#userList")[0].dataset.userId: null,//$(".userList")[0].dataset.userId,
            serviceId = $("#typeOfServiceList")[0].dataset.serviceId,
            statusId = $("#statusList")[0]?.dataset.statusId ? $("#statusList")[0].dataset.statusId: null,
            employeeUserId= $("#userEmployeeList")[0]?.dataset.userEmployeeId ? $("#userEmployeeList")[0].dataset.userEmployeeId : null,
            taskId = $('.modal-button-save')[0]?.dataset.id,
            task = {
                userId: userId ? parseInt(userId) : null,
                serviceId: serviceId ? parseInt(serviceId) : null,
                statusId: statusId ? parseInt(statusId) : null,
                employeeId: employeeUserId ? parseInt(employeeUserId) : null,
                id: taskId
            };
        $('.modal-button-save').removeAttr('data-id');


        $.ajax({
            url: '/tasks/update',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(task),
            success: function(response) {
                reloadTasks();
                hideAddTaskModal()
                showSuccessAlert();
            },
            error: function(error) {
                showDangerAlert("Ошибка обновления задачи");
                console.log(`Ошибка обновления задачи \n Error: ${error.message}`);
            }
        });
    }

    function deleteTask(e) {
        const taskId = e.currentTarget.dataset.taskId ? parseInt(e.currentTarget.dataset.taskId): null;
        const taskName = e.currentTarget.dataset.taskName ? e.currentTarget.dataset.taskName: null;
        const trTask = e.currentTarget.parentElement.parentElement;
        if (taskId != null && confirm("Вы уверены, что хотите удалить заявку " + taskName + "?")) {

            $.ajax({
                url: '/tasks/deleted',
                method: 'DELETE',
                contentType: 'application/json',
                data: JSON.stringify(taskId),
                success: function(data) {
                    if (data) {
                        //alert('Заявка успешно удалена');
                        trTask.remove() //??????????????????????
                        showSuccessAlert();
                    } else {
                        showDangerAlert("Ошибка при удалении заявки");
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Ошибка при удалении заявки:', error);
                    alert('Произошла ошибка при удалении заявки');
                }
            });
        }
    }

    function getTasksFilter(e) {
        e.preventDefault(); // Prevent default form submission

        const userId = $("#userFilter").val();
        const statusId = $("#statusFilter").val();
        const serviceId = $("#serviceFilter").val();
        const employeeId = $("#employeeFilter").val();

        const taskFilter = {
            userId: userId ? parseInt(userId) : null,
            serviceId: serviceId ? parseInt(serviceId) : null,
            statusId: statusId ? parseInt(statusId) : null,
            employeeId: employeeId ? parseInt(employeeId) : null
        };

        $.ajax({
            url: '/tasks/filters', // Adjusted to match your endpoint
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(taskFilter),
            success: function(response) {
                $('.table-striped tbody').empty();
                $.each(response, function(index, task) {
                    var row = $('<tr>').addClass(`task-id-${task.id}`)
                        .append($('<td>').text(task.id))
                        .append($('<td>').text(task.user.firstName + ' ' + task.user.lastName))
                        .append($('<td>').text(task.service.serviceType))
                        .append($('<td>').text(task.status.statusName))
                        .append($('<td>').text(task.employee ? (task.employee.user.firstName + ' ' + task.employee.user.lastName) : ''))
                        .append($('<td>')
                            .append($('<button>').addClass('btn btn-primary edit-task')
                                .attr('data-task', task.id + '-' + task.user.id + '-' + task.service.id + '-' + task.status.id + '-' + (task.employee ? task.employee.id : ''))
                                .text('Редактировать'))
                            .append($('<button>').addClass('btn btn-danger deleted-task')
                                .attr('data-task-id', task.id)
                                .attr('data-task-name', task.service.serviceType)
                                .text('Удалить')
                            )
                        );
                    $('.table-striped tbody').append(row);
                });
                $('.edit-task').off().on('click', showEditTasksModal);
                $('.deleted-task').off().on('click', deleteTask);
            },
            error: function(error) {
                showDangerAlert("Ошибка при поиске по фильтрам");
                console.log(`Ошибка при поиске по фильтрам \n Error: ${error.message}`);
            }
        });
    }

    // Функция для обновления задач
    function reloadTasks() {
        $.ajax({
            contentType: 'application/json',
            url: '/tasks/list',
            method: 'GET',
            success: function(response) {
                // Обновляем контент таблицы
                $('.table-striped tbody').empty();
                $.each(response, function(index, task) {
                    var row = $('<tr>').addClass(`task-id-${task.id}`)
                        .append($('<td>').text(task.id))
                        .append($('<td>').text(task.user.firstName + ' ' + task.user.lastName))
                        .append($('<td>').text(task.service.serviceType))
                        .append($('<td>').text(task.status.statusName))
                        .append($('<td>').text(task.employee ? (task.employee.user.firstName + ' ' + task.employee.user.lastName) : ''))
                        .append($('<td>')
                            .append($('<button>').addClass('btn btn-primary edit-task')
                                .attr('data-task', task.id + '-' + task.user.id + '-' + task.service.id + '-' + task.status.id + '-' + (task.employee ? task.employee.id : ''))
                                .text('Редактировать'))
                            .append($('<button>').addClass('btn btn-danger deleted-task')
                                .attr('data-task-id', task.id)
                                .attr('data-task-name', task.service.serviceType)
                                .text('Удалить')
                            )
                        );
                    $('.table-striped tbody').append(row);
                });
                $('.edit-task').off().on('click', showEditTasksModal);
                $('.deleted-task').off().on('click', deleteTask);
            },
            error: function(xhr, status, error) {
                console.error('Ошибка при получении списка задач:', error);
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