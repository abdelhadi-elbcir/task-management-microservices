import TaskManager from "./components/TaskManager";
import WebSocketClient from "./components/WebSocketClient";

function App() {
  return (
    <div>
      <h1>Real-Time Notifications</h1>
      <WebSocketClient />
    </div>
  );
}

export default App;
