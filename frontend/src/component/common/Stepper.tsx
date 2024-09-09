import clsx from 'clsx';

import { CheckIcon } from '@heroicons/react/24/solid';

const Stepper: React.FC<StepProps> = ({ currentStep, steps }) => {
  return (
    <div className="flex w-2/3 items-center justify-between">
      {Array.from({ length: steps }, (_, index) => {
        const stepNumber = index + 1;
        const isCompleted = stepNumber < currentStep;
        const isCurrent = stepNumber === currentStep;

        return (
          <div key={stepNumber} className="flex items-center">
            <div
              className={clsx(
                'flex size-10 items-center justify-center rounded-full',
                {
                  'bg-PRIMARY text-white': isCurrent,
                  'bg-BASE text-black': !isCompleted && !isCurrent,
                  'border-2 border-BASE bg-BASE text-black': isCompleted,
                },
              )}
            >
              {isCurrent ? (
                <CheckIcon className="size-5" />
              ) : (
                <span>{stepNumber}</span>
              )}
            </div>
            {stepNumber < steps && (
              <div className="mx-5 h-0.5 w-12 bg-DARKBASE" />
            )}
          </div>
        );
      })}
    </div>
  );
};

export default Stepper;
