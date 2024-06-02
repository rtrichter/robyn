import pyaudio as pa
import wave
import threading
import time


class AudioPlayer:

    chunk = 1024

    def __init__(self, filename: str, chunk: int = None):
        # TODO AudioPlayer.__init__ not implemented
        self.filename = filename
        self.chunk = AudioPlayer.chunk if chunk is None else chunk

        # pyaudio things
        self.wf = wave.open(filename, 'rb')
        self.pa = pa.PyAudio()
        self.stream = self.pa.open(
            format=self.pa.get_format_from_width(
                self.wf.getsampwidth()),
            channels=self.wf.getnchannels(),
            rate=self.wf.getframerate(),
            output=True)

        self.is_paused = False

    def _get_chunk(self):
        data = self.wf.readframes(self.chunk)
        return data

    def play(self) -> threading.Thread:
        def player(self):
            while (data := self._get_chunk()) != b'':
                self.stream.write(data)
                # stop playing when paused
                while self.is_paused:
                    time.sleep(0.001)

        play_thread = threading.Thread(
            target=player, args=(self,), daemon=False)
        play_thread.start()

        return play_thread

    def pause(self):
        self.is_paused = not self.is_paused

    def restart(self):
        raise NotImplementedError

    def rewind(self):
        raise NotImplementedError

    def fast_forward(self):
        raise NotImplementedError


if __name__ == '__main__':
    import sys
    if len(sys.argv) < 2:
        print("Please input a filename")
        sys.exit()
    player = AudioPlayer(sys.argv[1])
    player.play()
    time.sleep(1)
    player.pause()
    time.sleep(2)
    player.pause()
