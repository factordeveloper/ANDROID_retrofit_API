const express = require("express");
const mongoose = require("mongoose");
const bodyParser = require("body-parser");
const cors = require("cors");

const app = express();

app.use(cors());
app.use(bodyParser.json());

const MONGO_URI = "mongodb://localhost:27017/cyber_alerts";
mongoose.connect(MONGO_URI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
}).then(() => console.log("MongoDB connected"))
.catch(err => console.error("Error connecting to MongoDB:", err));

const alertSchema = new mongoose.Schema({
    type: String,
    message: String,
    timestamp: { type: Date, default: Date.now },
});

const Alert = mongoose.model("Alert", alertSchema);

app.post("/alerts", async (req, res) => {
    const { type, message } = req.body;

    if (!type || !message) {
        return res.status(400).json({ error: "Missing required fields" });
    }

    try {
        const alert = new Alert({ type, message });
        await alert.save();
        res.status(201).json(alert);
    } catch (error) {
        res.status(500).json({ error: "Failed to save alert" });
    }
});

app.get("/alerts", async (req, res) => {
    try {
        const alerts = await Alert.find();
        res.status(200).json(alerts);
    } catch (error) {
        res.status(500).json({ error: "Failed to fetch alerts" });
    }
});

const PORT = 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
