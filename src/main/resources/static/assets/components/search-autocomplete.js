import { LitElement, html } from "https://cdn.jsdelivr.net/gh/lit/dist@3/core/lit-core.min.js";

export class SearchAutocomplete extends LitElement {
    static properties = {
        keyword: { state: true },
        suggestions: { state: true },
        suggestUrl: { type: String, attribute: "suggest-url" },
        searchUrl: { type: String, attribute: "search-url" },
        htmxTarget: { type: String, attribute: "htmx-target" }
    };

    constructor() {
        super();
        this.keyword = "";
        this.suggestions = [];
        this.timer = null;

        this.suggestUrl = "/products/suggestions";
        this.searchUrl = "/products/search";
        this.htmxTarget = "#product-section";
    }

    createRenderRoot() {
        return this;
    }

    onInput(e) {
        this.keyword = e.target.value;
        clearTimeout(this.timer);

        if (!this.keyword.trim()) {
            this.suggestions = [];
            return;
        }

        this.timer = setTimeout(() => this.fetchSuggestions(), 300);
    }

    async fetchSuggestions() {
        if (!this.keyword.trim()) {
            this.suggestions = [];
            return;
        }

        const url = `${this.suggestUrl}?keyword=${encodeURIComponent(this.keyword)}`;
        const res = await fetch(url);

        if (res.ok) {
            this.suggestions = await res.json();
        }
    }

    chooseSuggestion(item) {
        this.keyword = item;
        this.suggestions = [];

        if (window.htmx) {
            const url = `${this.searchUrl}?keyword=${encodeURIComponent(item)}`;

            window.htmx.ajax("GET", url, {
                target: this.htmxTarget,
                swap: "innerHTML"
            });
        }
    }

    render() {
        return html`
            <style>
                .container {
                    position: relative;
                    width: 260px;
                }
                .dropdown {
                    position: absolute;
                    z-index: 1;
                    width: 100%;
                    background: white;
                    border: var(--wa-border-width-s) var(--wa-border-style) var(--wa-color-gray-90);
                    border-radius: var(--radius-base);
                    max-height: 240px;
                    overflow-y: auto;
                }
                .item {
                    padding: var(--wa-space-xs) var(--wa-space-s);
                    cursor: pointer;
                    border-radius: var(--radius-base);
                }
                .item:hover {
                    background: var(--wa-color-gray-90);
                }
            </style>
            
            <div class="container">

                <wa-input
                        id="search-input"
                        placeholder="Search by title..."
                        .value=${this.keyword}
                        @input=${this.onInput}
                ></wa-input>

                ${this.suggestions.length > 0 ? html`
                    <div class="dropdown">
                        ${this.suggestions.map(item => html`
                            <div
                                    class="item"
                                    @click=${() => this.chooseSuggestion(item)}
                                    hx-get="${this.searchUrl}?keyword=${encodeURIComponent(item)}"
                                    hx-target="${this.htmxTarget}"
                                    hx-swap="innerHTML">
                                ${item}
                            </div>
                        `)}
                    </div>
                ` : ""}
            </div>
        `;
    }
}

customElements.define("search-autocomplete", SearchAutocomplete);
