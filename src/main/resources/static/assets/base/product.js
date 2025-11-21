document.addEventListener('DOMContentLoaded', () => {
    attachProductFormEvents()
});

document.getElementById('create-product-btn').addEventListener('click', function () {
    openDialog()
});
document.body.addEventListener('htmx:afterSwap', function (evt) {
    if (evt.target.id === 'product-section') {
        closeDialog()
    }
});

function openDialog() {
    const dialog = document.querySelector('.dialog-create-product');
    dialog.open = true;
}

function closeDialog() {
    const dialog = document.querySelector('.dialog-create-product');
    dialog.open = false;
}

function attachProductFormEvents() {
    const addVariantBtn = document.getElementById('add-variant-btn');
    if (addVariantBtn) {
        addVariantBtn.onclick = function () {
            const container = document.getElementById('variants-container');
            const blocks = container.querySelectorAll('.variant-block');
            const newIndex = blocks.length;
            const block = blocks[0].cloneNode(true);

            block.querySelectorAll('wa-input, wa-select, input[type="checkbox"]').forEach(inp => {
                let name = inp.getAttribute('name');
                if (!name) return;
                name = name.replace(/\[\d+\]/, `[${newIndex}]`);
                inp.setAttribute('name', name);
                console.log(inp.tagName.toLowerCase(), inp.type)
                if (inp.tagName.toLowerCase() === 'wa-checkbox' && inp.type === '') {
                    const field = inp.id.replace(/\d+$/, '');
                    const newId = field + newIndex;
                    inp.id = newId;
                    const label = inp.parentElement.querySelector('label[for]');
                    if (label) label.setAttribute('for', newId);
                    inp.checked = false;
                    block.querySelectorAll('input[type="hidden"][name="' + name + '"]').forEach(h => h.remove());
                    const hidden = document.createElement('input');
                    hidden.type = 'hidden';
                    hidden.name = name;
                    hidden.value = 'false';
                    inp.parentElement.insertBefore(hidden, inp);
                } else {
                    inp.value = '';
                }
            });
            block.querySelectorAll('input[type="hidden"]').forEach(h => h.remove());
            container.appendChild(block);
            updateVariantLabels();
        };
    }

    const addImageBtn = document.getElementById('add-image-btn');
    if (addImageBtn) {
        addImageBtn.onclick = function () {
            const container = document.getElementById('images-container');
            const blocks = container.querySelectorAll('.image-block');
            const newIndex = blocks.length;
            const block = blocks[0].cloneNode(true);

            block.querySelectorAll('wa-input, input[type="hidden"]').forEach(inp => {
                let name = inp.getAttribute('name');
                if (!name) return;
                name = name.replace(/\[\d+\]/, `[${newIndex}]`);
                inp.setAttribute('name', name);
                if (inp.type === 'hidden') {
                    inp.value = '';
                } else {
                    inp.value = '';
                }
            });
            container.appendChild(block);
            updateImageLabels();
        };
    }

    function updateVariantLabels() {
        const blocks = document.querySelectorAll('#variants-container .variant-block');
        blocks.forEach((block, idx) => {
            let label = block.querySelector('.variant-label');
            if (!label) {
                label = document.createElement('div');
                label.className = 'variant-label';
                label.style.position = 'absolute';
                label.style.top = '0.5rem';
                label.style.right = '1rem';
                label.style.fontSize = '0.9rem';
                label.style.color = '#888';
                block.appendChild(label);
            }
            label.textContent = `Variant #${idx + 1}`;
        });
    }

    function updateImageLabels() {
        const blocks = document.querySelectorAll('#images-container .image-block');
        blocks.forEach((block, idx) => {
            let label = block.querySelector('.image-label');
            if (!label) {
                label = document.createElement('div');
                label.className = 'image-label';
                label.style.position = 'absolute';
                label.style.top = '0.5rem';
                label.style.right = '1rem';
                label.style.fontSize = '0.9rem';
                label.style.color = '#888';
                block.appendChild(label);
            }
            label.textContent = `Image #${idx + 1}`;
        });
    }
}