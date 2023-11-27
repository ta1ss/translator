<script>
  import { onMount } from 'svelte';
  import axios from 'axios';
  import { v4 as uuidv4 } from "uuid";
  import { Link } from "svelte-routing";

  let translationHistory = [];
  let sessionID = "";

  onMount(async () => {
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
        await fetchTranslationHistory();
  });

  const fetchTranslationHistory = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/history?sessionID=${sessionID}`
      );
      translationHistory = response.data;
    } catch (error) {
      console.error('Failed to fetch translation history', error);
    }
  };

  const formatTimestamp = (timestamp) => {
    const date = new Date(timestamp);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();

    return `${hours}:${minutes}, ${day}/${month}/${year}`;
  };

  const clearHistory = async () => {
    try {
        await axios.delete('http://localhost:8080/api/history/clear', {
            withCredentials: true,
        });
        
        translationHistory = [];
    } catch (error) {
        console.error('Failed to clear translation history', error);
    }
};
</script>

<main class="p-8 flex flex-col items-center mt-10">
    <div class="w-full mt-2 flex justify-between items-center">
      <Link to="/">
        <button>
            Home
        </button>
    </Link>
        <h2 class="text-xl font-bold text-center flex-grow ml-8 mb-8">Translation history</h2>
        <button on:click={clearHistory}>
            Clear History
        </button>
    </div>
        {#if translationHistory.length > 0}
            {#each translationHistory.slice().reverse() as translation}
                <div class="flex flex-col border border-gray-100 rounded p-4 mb-4 mt-12">
                    <div class="flex justify-between mb-4">
                        <p class="mr-6 mb-2">Source Language: <strong>{translation.fromLanguage}</strong></p>
                        <p class="mr-6">Timestamp: <strong>{formatTimestamp(translation.timestamp)}</strong></p>
                        <p>Translated Language: <strong>{translation.toLanguage}</strong></p>
                    </div>
                    <div class="flex">
                        <textarea class="w-1/2 p-2 border border-gray-300 rounded mr-2" disabled readonly style="resize: none;">{translation.originalText}</textarea>
                        <textarea class="w-1/2 p-2 border border-gray-300 rounded ml-2" disabled readonly style="resize: none;">{translation.translatedText}</textarea>
                    </div>
                </div>
            {/each}
        {:else}
            <p class="mt-14">No translation history found.</p>
        {/if}
</main>
