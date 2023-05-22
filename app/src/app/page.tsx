import Image from 'next/image'
import Names from './names'

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col justify-items-center p-24">

      <Image
        className="dark:drop-shadow-[0_0_0.3rem_#ffffff70] dark:invert"
        src="/logo_brussels_grey_on_trans.png"
        alt="Voxxed Days Brussels Logo"
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
