function updateTicketStatus(ticketId, newStatus) {
    if (!newStatus) {
        return;
    }
    fetch(`/web/spec/tickets/status?ticketId=${ticketId}&newStatus=${newStatus}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
        }
    })
        .then(response => {
            if (response.ok) {
                alert("Статус успешно изменён.");
                location.reload();
            } else {
                throw new Error("Не удалось закрыть заявку.");
            }

        })
        .catch(error => {
            alert("Ошибка: " + error.message);
        });
}
