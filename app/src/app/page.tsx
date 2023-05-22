import Image from 'next/image'
import Names from './names'

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col justify-items-center p-24">

      <Image
        className="dark:drop-shadow-[0_0_0.3rem_#ffffff70] mb-4 light:invert"
        src="/AOTBwhitelogo.png"
        alt="Agile On The Beach Logo"
        width={180}
        height={37}
        priority
      />

      <div>
        <Names />
      </div>
    </main>
  )
}
