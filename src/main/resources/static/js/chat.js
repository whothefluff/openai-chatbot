// Placeholder for WebSocket handling script
// This will be filled with the functionality to connect to the server and send/receive messages
console.log("Chat script loaded");

function updateSliderValue(sliderId, displayId) {
    const slider = document.getElementById(sliderId);
    document.getElementById(displayId).textContent = slider.value;
}

function toggleContainerInputs(checkboxElement, containerId) {
    const container = document.getElementById(containerId);
    const inputs = container.querySelectorAll('input, textarea, select, button');
    const shouldBeEnabled = checkboxElement.checked;
    container.classList.toggle('d-none', !shouldBeEnabled);
    inputs.forEach(input => {
        input.disabled = !shouldBeEnabled;
    });
}

function autoGrow(element, maxHeight) {
    const minHeight = 75; // Assuming a row height of 20px
    element.style.height = "5px"; // Temporarily shrink to get the correct scrollHeight
    const newHeight = Math.min(element.scrollHeight, maxHeight);
    element.style.height = (newHeight > minHeight ? newHeight : minHeight) + "px";
    // Add scrollbar if the content is larger than maxHeight
    element.style.overflowY = newHeight >= maxHeight ? "scroll" : "hidden";
}

function formatJson(textarea) {
    try {
        let json = JSON.parse(textarea.value);
        textarea.value = JSON.stringify(json, null, 2);
    } catch (e) {
        console.error("The text is not valid JSON.");
    }
}
