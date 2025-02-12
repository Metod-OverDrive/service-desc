function closeTicket(ticketId) {
    if (confirm("Вы уверены, что хотите закрыть эту заявку?")) {
        fetch(`/web/tickets?ticketId=${ticketId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert("Заявка успешно закрыта.");
                    location.reload();
                } else {
                    throw new Error("Не удалось закрыть заявку.");
                }
            })
            .catch(error => {
                alert("Ошибка: " + error.message);
            });
    }
}