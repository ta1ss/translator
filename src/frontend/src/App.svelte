<script>
  import { Route, Router } from 'svelte-routing';
  import Home from './lib/Home.svelte'
  import History from './lib/History.svelte'
</script>

<Router>
  <Route path="/" component={Home} />
  <Route path="/history" component={History} />
</Router>
