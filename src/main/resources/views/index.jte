@param String response
@param String ticket
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="author" content="Mostly ChatGPT" />
    <meta name="prompt" content="You're a talented DevOOPS, produce a single html page with an input asking for a Ticket id, and on submit it redirects to /tickets/<ticket>" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Ticket Disaster</title>
    <style>
        :root{font-family:system-ui,-apple-system,Segoe UI,Roboto,'Helvetica Neue',Arial;}
        body{display:grid;justify-content:center;margin:0;background:#f6f7fb}
        .card{background:white;padding:28px;border-radius:12px;box-shadow:0 6px 20px rgba(20,30,60,0.08);width:800px; margin-top:60px;}
        h1{font-size:18px;margin:0 0 12px}
        label{display:block;font-size:13px;margin-bottom:8px}
        input[type=text]{width:100%;padding:10px 12px;border:1px solid #dfe3ee;border-radius:8px;font-size:14px;box-sizing:border-box}
        textarea{width:100%;padding:10px 12px;border:1px solid #dfe3ee;border-radius:8px;font-size:14px;box-sizing:border-box}
        .row{display:flex;gap:8px;margin-top:12px}
        button{flex:1;padding:10px 12px;border-radius:8px;border:0;background:#2b6ef6;color:#fff;font-weight:600;cursor:pointer}
        .help{margin-top:10px;font-size:12px;color:#666}
        .error{color:#b00020;font-size:13px;margin-top:8px}
        .success{color:#4bb543;font-size:16px;font-weight:bold;margin-top:8px}
    </style>
</head>
<body>
<main class="card" role="main">
    <h1>1. Produce a Ticket Request in Kafka</h1>
    <p class="help">Produce a single message in topic <b>tickets</b> with the record value as your Ticket. Key will be ignored. Messages longer than 50 bytes will be ignored.</p>

    <label for="config"><code>config.properties</code></label>
    <textarea id="config" cols="40" rows="5">
bootstrap.servers=195.154.119.34:9092
security.protocol=SASL_PLAINTEXT
sasl.mechanism=PLAIN
sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="user1" password="user1-secret";</textarea>
    <label for="config"><code>App.java</code></label>
    <textarea id="config" cols="40" rows="3">
Producer&lt;String, String&gt; producer = new KafkaProducer<>(props);
var record = new ProducerRecord("tickets", my_ticket);
producer.send(record).get();
    </textarea>
</main>
<main class="card" role="main">
    <h1>2. Check your Ticket Status below</h1>
    <form id="ticketForm" action="/tickets/" method="get" novalidate>
        <label for="ticketId">Ticket ID</label>
        <input id="ticketId" value="${ticket}" name="ticketId" type="text" inputmode="text" autocomplete="off" placeholder="e.g. ABC-123 or 98765" required />
        <div class="row">
            <button id="goBtn" type="submit">Get ticket</button>
        </div>
        <p class="help">Enter the ticket id above and press <strong>Open ticket</strong>. You will be redirected to <code>/tickets/&lt;your-id&gt;</code>.</p>
        @if(response != null && response.startsWith("Success"))
            <p class="success" aria-live="polite">${response}</p>
            <div style="width:100%;height:0;padding-bottom:67%;position:relative;"><iframe src="https://giphy.com/embed/Tp0DoOS8C7eBG" width="100%" height="100%" style="position:absolute" frameBorder="0" class="giphy-embed" allowFullScreen></iframe></div>
        @else
            <p id="error" class="error" aria-live="polite">${response}</p>
        @endif

    </form>
</main>

<script>
    (function(){
      const form = document.getElementById('ticketForm');
      const input = document.getElementById('ticketId');
      const error = document.getElementById('error');

      form.addEventListener('submit', function(e){
        e.preventDefault();
        error.hidden = true;
        const raw = input.value.trim();
        if(!raw){
          error.textContent = 'Please enter a ticket id.';
          error.hidden = false;
          input.focus();
          return;
        }
        const safe = encodeURIComponent(raw);
        window.location.href = '/tickets/' + safe;
      });
    })();
</script>
</body>
</html>
