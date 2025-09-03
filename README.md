# Simple Rock Paper Scissors

**A small app that taught me how to code... and a lot more.**

## The Story

My first real taste of programming wasn't Android, it was on Windows 7, trying to make an "Oregon Trail"-style text adventure in **batch**. It didn't get very far and quickly turned into a tangled spider web of choices, but it gave me a glimpse of what programming could be.

Years later, I found myself nitpicking the mobile apps I used every day. I kept thinking, *"I wish I could tweak this one little thing."* That's what pushed me into mobile app development: the idea that I could actually make those tweaks myself.

The first result of that curiosity was **Simple Rock Paper Scissors**. On the surface, it's just the classic game, but for me, it was my crash course in learning how to build apps.

A funny thing about the earliest version: I used massively huge PNGs for the icons. On high-end devices, that caused a short delay after you picked a move, like a built-in pause. On lower-end devices, it would likely crash the phone... not the app, the entire phone would shut-down and reboot. Yeah, not ideal. Eventually, I learned about SVGs and swapped them in, which fixed the performance mess.

The UI was also hardcoded to line up perfectly on **my phone only**. On other devices, it was a disaster (seeing a pattern here?). Buttons everywhere. I learned about responsive design the hard way. and slowly made the app look right across different screen sizes.

Over the years, I kept adding little touches: vibrations, background gradients, dark/light theme support. I even added a scoreboard... though a later UI update would remove the scoreboard UI. The logic still tracks and the score between the player and CPU is still saved in the background, but the display never came back.


## The Journey
After working on my other app, **Pebble**, I’d circle back to Rock Paper Scissors every so often, cleaning things up and making improvements. Eventually, I migrated the whole thing from Java to **Kotlin**, removing duplicated code and applying best practices I picked up over time.

The app was on the **Google Play Store** for years alongside a few other of my apps, but it was eventually removed when Google introduced a policy requiring developers to publicly display personal info (email, phone number, and home address) if they ever used ads or in-app purchases, even if they weren't using them anymore. Not wanting my address made public, I didn't comply, which meant my apps came down. Now, for the most part, they're **open source**.


## Why This Matters
This simple little app was my entry point into programming, and it's why I've stuck with it all these years. It taught me:

- How to design responsive UIs that work everywhere
- Why resource choices (like PNG vs SVG) really matter
- How to debug weird bugs (like "my phone reboots when I play my game")
- How to maintain and improve a project over time
- How to go from **Java spaghetti code** to a cleaner, Kotlin-based project (and no, it wasn't Java's fault)

I don't update it often anymore, it usually only gets attention when I'm bored, but it'll always be my first real project.


# Where I Am Now
These days, I'm exploring other creative outlets, like storytelling. But Simple Rock Paper Scissors will always be the project that started it all: a small app, a bunch of fun mistakes I still laugh at, and a turning point that pulled me into the world of programming. I was 14 years old when I spent the $25 for a Google Developer account (with parental support of course), and 15 years old when my first app went live.
