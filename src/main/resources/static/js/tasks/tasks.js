$(document).ready(function() {
    $('.add-task').on('click', showAddTaskModal);


    function showAddTaskModal() {
        $('#taskModal').modal('show');
    }
});