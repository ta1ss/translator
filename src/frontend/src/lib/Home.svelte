<script>
    import { onMount } from "svelte";
    import axios from "axios";
    import { v4 as uuidv4 } from "uuid";
    import { Link } from 'svelte-routing';

    let text = "";
    let fromLanguage = "en";
    let toLanguage = "fr";
    let translatedText = "";
    let sessionID = "";

    onMount(() => {
        const cookies = document.cookie
            .split(";")
            .map((cookie) => cookie.trim());
        const sessionCookie = cookies.find((cookie) =>
            cookie.startsWith("sessionID="),
        );

        if (sessionCookie) {
            sessionID = sessionCookie.split("=")[1];
        } else {
            sessionID = uuidv4();
            document.cookie = `sessionID=${sessionID}; path=/`;
        }
    });

    const translateText = async () => {
        try {
            const response = await axios.post(
                "http://localhost:8080/api/translate",
                {
                    text,
                    fromLanguage,
                    toLanguage,
                },
                {
                    withCredentials: true,
                },
            );
            translatedText = response.data.translatedText;
        } catch (error) {
            console.error("Translation failed", error);
        }
    };
</script>

<main class="p-8 flex flex-col items-center mt-10">
    <div class="flex flex-col items-center justify-center w-full" >
        <div class="flex w-full justify-between mb-8">
            <div class="flex flex-col items-center w-1/3">
                <select
                    class="p-3 border border-gray-300 rounded mb-1 -mr-20"
                    bind:value={fromLanguage}
                >
                <option value="en">English</option>
                <option value="fr">French</option>
                <option value="es">Spanish</option>
                <option value="de">German</option>
                <option value="it">Italian</option>
                <option value="ja">Japanese</option>
                </select>
            </div>
    
            <button
                on:click={translateText}
                style="flex: 0.1; padding-left: 10%; padding-right: 10%;"
            >
                Translate
            </button>
    
            <div class="flex flex-col items-center w-1/3">
                <select
                    class="p-3 border border-gray-300 rounded mb-1 -ml-20"
                    bind:value={toLanguage}
                >
                <option value="fr">French</option>
                <option value="en-US">English</option>
                <option value="es">Spanish</option>
                <option value="de">German</option>
                <option value="it">Italian</option>
                <option value="ja">Japanese</option>
                <option value="ru">Russian</option>
                <option value="ar">Arabic</option>
                <option value="hi">Hindi</option>
                <option value="ko">Korean</option>
                <option value="pt">Portuguese</option>
                <option value="nl">Dutch</option>
                <option value="tr">Turkish</option>
                <option value="pl">Polish</option>
                <option value="sv">Swedish</option>
                <option value="uk">Ukrainian</option>
                <option value="vi">Vietnamese</option>
                </select>
            </div>
        </div>

        <div class="flex w-full justify-between">
            <textarea
                class="w-1/2 p-4 border border-gray-300 rounded mb-2 mr-5"
                placeholder="Enter text to translate.."
                style="resize: none;"
                bind:value={text}
                rows="10"
                cols="50"
            ></textarea>
            <textarea
                class="w-1/2 p-4 border border-gray-300 rounded mb-2"
                placeholder="Translated text.."
                style="resize: none;"
                bind:value={translatedText}
                rows="10"
                cols="50"
                disabled
            ></textarea>
        </div>
        <Link to="/history" class="self-end mt-4">
            <button>
                History
            </button>
        </Link>
    </div>
</main>
